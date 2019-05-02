package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.RoleInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.RoleUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.torderadmin.util.ErrorCodeUtils;
import club.mrdaisite.torder.tordermbg.mapper.AdminRoleRelationMapper;
import club.mrdaisite.torder.tordermbg.mapper.RoleMapper;
import club.mrdaisite.torder.tordermbg.mapper.RolePermissionRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<Object> listRole(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Role> roleList = roleMapper.selectByExample(new RoleExample());
        PageInfo pageInfo = new PageInfo<>(roleList);
        return pageInfo.getList();
    }

    @Override
    public List<Role> listRoleByName(String roleName) {
        RoleExample roleExample = new RoleExample();
        roleExample.or().andNameEqualTo(roleName);
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Role getRoleByAdminId(Long id) {
        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andAdminIdEqualTo(id);
        List<AdminRoleRelation> adminRoleRelationList = adminRoleRelationMapper.selectByExample(adminRoleRelationExample);
        return roleMapper.selectByPrimaryKey(adminRoleRelationList.get(0).getRoleId());
    }

    @Override
    public Role insertRole(RoleInsertParamDTO roleInsertParamDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleInsertParamDTO, role);
        role.setGmtCreate(new Date());
        role.setGmtModified(new Date());
        roleMapper.insert(role);
        return role;
    }

    @Override
    public Role updateRole(Long id, RoleUpdateParamDTO roleUpdateParamDTO) {
        Role role = roleMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(roleUpdateParamDTO, role);
        role.setGmtModified(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
        return role;
    }

    @Override
    public void deleteRole(Long id) {
        AdminRoleRelationExample adminRoleRelationExample = new AdminRoleRelationExample();
        adminRoleRelationExample.or().andRoleIdEqualTo(id);
        RolePermissionRelationExample rolePermissionRelationExample = new RolePermissionRelationExample();
        rolePermissionRelationExample.or().andRoleIdEqualTo(id);
        adminRoleRelationMapper.deleteByExample(adminRoleRelationExample);
        rolePermissionRelationMapper.deleteByExample(rolePermissionRelationExample);
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void roleExists(Long id) throws CustomNotFoundException {
        Role role = roleMapper.selectByPrimaryKey(id);
        Optional.ofNullable(role)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4046000).getEMessage()));
    }
}
