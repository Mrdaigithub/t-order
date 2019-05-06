package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.common.util.ErrorCodeUtils;
import club.mrdaisite.torder.torderadmin.dto.PermissionInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.PermissionUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminPermissionService;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.torderadmin.service.AdminAdminService;
import club.mrdaisite.torder.tordermbg.mapper.PermissionMapper;
import club.mrdaisite.torder.tordermbg.mapper.RolePermissionRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {
    @Autowired
    private AdminAdminService adminAdminService;
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<Permission> listPermission() {
        return permissionMapper.selectByExample(new PermissionExample());
    }

    @Override
    public List<Permission> listPermissionByUsername(String username) throws CustomNotFoundException {
        List<Permission> permissionList = new ArrayList<>();
        Admin admin = adminAdminService.getAdminByUsername(username);
        Role role = adminRoleService.getRoleByAdminId(admin.getId());
        RolePermissionRelationExample rolePermissionRelationExample = new RolePermissionRelationExample();
        rolePermissionRelationExample.or().andRoleIdEqualTo(role.getId());
        List<RolePermissionRelation> rolePermissionRelationList = rolePermissionRelationMapper.selectByExample(rolePermissionRelationExample);
        for (RolePermissionRelation rolePermissionRelation : rolePermissionRelationList) {
            Permission permission = permissionMapper.selectByPrimaryKey(rolePermissionRelation.getPermissionId());
            if (permission != null) {
                permissionList.add(permission);
            }
        }
        return permissionList;
    }

    @Override
    public Permission getPermissionById(Long id) throws CustomNotFoundException {
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        Optional.ofNullable(permission)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4047000).getEMessage()));
        return permission;
    }

    @Override
    public Permission getPermissionByPermissionValue(String permissionValue) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.or().andValueEqualTo(permissionValue);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        return permissionList.get(0);
    }

    @Override
    public Permission insertPermission(PermissionInsertParamDTO permissionInsertParamDTO) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionInsertParamDTO, permission);
        permission.setGmtCreate(new Date());
        permission.setGmtModified(new Date());
        permissionMapper.insert(permission);
        return permission;
    }

    @Override
    public Permission updatePermission(Long id, PermissionUpdateParamDTO permissionUpdateParamDTO) {
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(permissionUpdateParamDTO, permission);
        permission.setGmtModified(new Date());
        permissionMapper.updateByPrimaryKeySelective(permission);
        return permission;
    }

    @Override
    public void deletePermission(Long id) throws CustomNotFoundException {
        Permission permission = getPermissionById(id);
        RolePermissionRelationExample rolePermissionRelationExample = new RolePermissionRelationExample();
        rolePermissionRelationExample.or().andRoleIdEqualTo(id);
        rolePermissionRelationMapper.deleteByExample(rolePermissionRelationExample);
        permissionMapper.deleteByPrimaryKey(id);
    }
}
