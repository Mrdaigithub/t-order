package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.AdminInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminResultDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminAdminService;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.tordermbg.mapper.AdminMapper;
import club.mrdaisite.torder.tordermbg.mapper.AdminRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dai
 * @date 2019/03/21
 */
@Service
public class AdminAdminServiceImpl implements AdminAdminService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;

    @Override
    public List<Object> listAdmin(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Admin> adminList = adminMapper.selectByExample(new AdminExample());
        PageInfo pageInfo = new PageInfo<>(adminList);
        List<Object> pageInfoList = pageInfo.getList();
        List<Object> targetList = new ArrayList<>();
        for (int i = 0; i < pageInfoList.size(); i++) {
            targetList.add(new AdminResultDTO());
        }
        return new FuncUtils().beanUtilsCopyListProperties(pageInfoList, targetList);
    }

    @Override
    public List<Admin> listAdminByRoleId(Long roleId) throws CustomNotFoundException {
        if (!adminRoleService.roleExists(roleId)) {
            new ErrorCodeUtils(4046000).throwNotFoundException();
        }
        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andRoleIdEqualTo(roleId);
        List<AdminRoleRelation> adminRoleRelationList = adminRoleRelationMapper.selectByExample(adminRoleRelationExample);
        return adminRoleRelationList.stream()
                .map(adminRoleRelation -> adminMapper.selectByPrimaryKey(adminRoleRelation.getAdminId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public AdminResultDTO getAdminById(Long id) throws CustomNotFoundException {
        AdminResultDTO adminResultDTO = new AdminResultDTO();
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            new ErrorCodeUtils(4041000).throwNotFoundException();
        }
        assert admin != null;
        BeanUtils.copyProperties(admin, adminResultDTO);
        return adminResultDTO;
    }

    @Override
    public Admin getAdminByUsername(String username) throws CustomNotFoundException {
        AdminExample adminExample = new AdminExample();
        adminExample.or().andUsernameEqualTo(username);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (adminList == null || adminList.size() <= 0) {
            new ErrorCodeUtils(4041000).throwNotFoundException();
        }
        assert adminList != null;
        return adminList.get(0);
    }

    @Override
    public AdminResultDTO insertAdmin(AdminInsertParamDTO adminInsertParamDTO, String roleName) throws CustomNotFoundException {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminInsertParamDTO, admin);
        admin.setPassword(bCryptPasswordEncoder.encode(adminInsertParamDTO.getPassword()));
        adminMapper.insertSelective(admin);

        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameEqualTo(roleName);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        if (roleList == null || roleList.size() <= 0) {
            new ErrorCodeUtils(4046000).throwNotFoundException();
        }
        assert roleList != null;
        Role role = roleList.get(0);

        AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
        adminRoleRelation.setAdminId(admin.getId());
        adminRoleRelation.setRoleId(role.getId());
        adminRoleRelationMapper.insertSelective(adminRoleRelation);

        return getAdminById(admin.getId());
    }

    @Override
    public AdminResultDTO updateAdmin(Long id, AdminUpdateParamDTO adminUpdateParamDTO) throws CustomNotFoundException {
        if (!adminExists(id)) {
            new ErrorCodeUtils(4041000).throwNotFoundException();
        }
        Admin admin = adminMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(adminUpdateParamDTO, admin);
        if (admin.getPassword() != null) {
            admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        }
        adminMapper.updateByPrimaryKeySelective(admin);
        return getAdminById(id);
    }

    @Override
    public void deleteAdmin(Long id) throws CustomNotFoundException {
        if (!adminExists(id)) {
            new ErrorCodeUtils(4041000).throwNotFoundException();
        }
        Admin admin = adminMapper.selectByPrimaryKey(id);
        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andAdminIdEqualTo(admin.getId());
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean adminExists(Long id) {
        return adminMapper.selectByPrimaryKey(id) != null;
    }
}
