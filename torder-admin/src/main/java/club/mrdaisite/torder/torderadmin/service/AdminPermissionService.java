package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.tordermbg.model.Permission;

import java.util.List;

/**
 * AdminService
 *
 * @author dai
 * @date 2019/04/17
 */
public interface AdminPermissionService {
    /**
     * 获取指定用户的权限列表
     *
     * @param username 用户名
     * @return 指定用户的权限列表
     */
    List<Permission> getPermissionListByUsername(String username);

    /**
     * 根据权限值获取权限
     *
     * @param permissionValue 权限值
     * @return 指定权限
     */
    Permission getPermissionByPermissionValue(String permissionValue);
}
