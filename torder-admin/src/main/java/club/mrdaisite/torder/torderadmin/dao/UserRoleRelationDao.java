package club.mrdaisite.torder.torderadmin.dao;

import club.mrdaisite.torder.tordermbg.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserRoleRelationDao
 *
 * @author dai
 * @date 2019/03/27
 */
public interface UserRoleRelationDao {
    /**
     * 获取指定用户的权限列表
     *
     * @param userId 用户id
     * @return 指定用户的权限列表
     */
    List<Permission> getPermissionList(@Param("userId") Long userId);
}
