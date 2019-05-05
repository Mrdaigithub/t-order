package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.common.api.CommonPage;
import club.mrdaisite.torder.torderadmin.dto.AdminInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminResultDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomForbiddenException;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminAdminService;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.common.util.ErrorCodeUtils;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.tordermbg.mapper.AdminMapper;
import club.mrdaisite.torder.tordermbg.mapper.AdminRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public CommonPage listAdmin(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Admin> adminList = adminMapper.selectByExample(new AdminExample());
        PageInfo pageInfo = new PageInfo<>(adminList);
        List<Object> pageInfoList = pageInfo.getList();
        List<Object> targetList = new ArrayList<>();
        for (int i = 0; i < pageInfoList.size(); i++) {
            targetList.add(new AdminResultDTO());
        }
        return new CommonPage(pageInfo, new FuncUtils().beanUtilsCopyListProperties(pageInfoList, targetList));
    }

    @Override
    public List<Admin> listAdminByRoleId(Long roleId) throws CustomNotFoundException, CustomForbiddenException {
        adminRoleService.roleExists(roleId);
        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andRoleIdEqualTo(roleId);
        List<AdminRoleRelation> adminRoleRelationList = adminRoleRelationMapper.selectByExample(adminRoleRelationExample);
        Optional.ofNullable(adminRoleRelationList)
                .filter(adminRoleRelations -> adminRoleRelations.size() > 0)
                .orElseThrow(() -> new CustomForbiddenException(new ErrorCodeUtils(4041001).getEMessage()));
        return adminRoleRelationList.stream()
                .map(adminRoleRelation -> adminMapper.selectByPrimaryKey(adminRoleRelation.getAdminId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public AdminResultDTO getAdminById(Long id) throws CustomNotFoundException {
        AdminResultDTO adminResultDTO = new AdminResultDTO();
        Admin admin = adminMapper.selectByPrimaryKey(id);
        Optional.ofNullable(admin)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4041000).getEMessage()));
        BeanUtils.copyProperties(admin, adminResultDTO);
        return adminResultDTO;
    }

    @Override
    public Admin getAdminByUsername(String username) throws CustomNotFoundException {
        AdminExample adminExample = new AdminExample();
        adminExample.or().andUsernameEqualTo(username);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        Optional.ofNullable(adminList)
                .filter(admins -> admins.size() > 0)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4041000).getEMessage()));
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
        Optional.ofNullable(roleList)
                .filter(roles -> roles.size() > 0)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4046000).getEMessage()));
        Role role = roleList.get(0);

        AdminRoleRelation adminRoleRelation = new AdminRoleRelation();
        adminRoleRelation.setAdminId(admin.getId());
        adminRoleRelation.setRoleId(role.getId());
        adminRoleRelationMapper.insertSelective(adminRoleRelation);

        return getAdminById(admin.getId());
    }

    @Override
    public AdminResultDTO updateAdmin(Long id, AdminUpdateParamDTO adminUpdateParamDTO) throws CustomNotFoundException {
        adminExists(id);
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
        adminExists(id);
        Admin admin = adminMapper.selectByPrimaryKey(id);
        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andAdminIdEqualTo(admin.getId());
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void adminExists(Long id) throws CustomNotFoundException {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        Optional.ofNullable(admin)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4041000).getEMessage()));
    }
}
