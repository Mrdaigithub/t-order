package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.dto.PermissionInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.PermissionUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.tordermbg.model.Permission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AdminService
 *
 * @author dai
 * @date 2019/04/17
 */
public interface AdminPermissionService {
    /**
     * 获取所有权限列表
     *
     * @return 所有权限列表
     */
    List<Permission> listPermission();

    /**
     * 获取指定用户的权限列表
     *
     * @param username 用户名
     * @return 指定用户的权限列表
     */
    List<Permission> listPermissionByUsername(String username) throws CustomNotFoundException;

    /**
     * 根据id获取指定权限
     *
     * @param id 权限id
     * @return 指定权限
     */
    Permission getPermissionById(Long id);

    /**
     * 根据权限值获取权限
     *
     * @param permissionValue 权限值
     * @return 指定权限
     */
    Permission getPermissionByPermissionValue(String permissionValue);

    /**
     * 添加权限
     *
     * @param permissionInsertParamDTO 权限参数
     * @return 返回添加的权限信息
     */
    Permission insertPermission(PermissionInsertParamDTO permissionInsertParamDTO);

    /**
     * 修改权限信息
     *
     * @param id                 权限id
     * @param permissionUpdateParamDTO 修改后的权限参数
     * @return 修改后的权限信息
     */
    Permission updatePermission(Long id, PermissionUpdateParamDTO permissionUpdateParamDTO);

    /**
     * 删除权限
     *
     * @param id 权限id
     */
    @Transactional(rollbackFor = Exception.class)
    void deletePermission(Long id);

    /**
     * 判断权限是否存在
     *
     * @param id 权限id
     * @return 权限是否存在
     */
    Boolean permissionExists(Long id);
}
