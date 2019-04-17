package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.service.AdminPermissionService;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.torderadmin.service.AdminUserService;
import club.mrdaisite.torder.tordermbg.mapper.PermissionMapper;
import club.mrdaisite.torder.tordermbg.mapper.RolePermissionRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dai
 * @date 2019/04/17
 */
@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminRoleService adminRoleService;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Override
    public List<Permission> getPermissionListByUsername(String username) {
        List<Permission> permissionList = new ArrayList<>();
        User user = adminUserService.getUserByUsername(username);
        Role role = adminRoleService.getRoleByUsername(user.getUsername());
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
    public Permission getPermissionByPermissionValue(String permissionValue) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.or().andValueEqualTo(permissionValue);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        return permissionList.get(0);
    }
}
