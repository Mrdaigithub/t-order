package club.mrdaisite.torder.torderadmin.util;

import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.tordermbg.model.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

/**
 * FuncUtils
 *
 * @author dai
 * @date 2019/04/11
 */
public class FuncUtils {
    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 复制列表属性
     *
     * @param sourceList 被复制的列表
     * @param targetList 最终列表
     * @return 最终的列表
     */
    public List<Object> beanUtilsCopyListProperties(List<Object> sourceList, List<Object> targetList) {
        for (int i = 0; i < targetList.size(); i++) {
            BeanUtils.copyProperties(sourceList.get(i), targetList.get(i));
        }
        return targetList;
    }

    /**
     * 判断当前用户是否有权限操作指定角色组
     *
     * @param id       当前用户id
     * @param roleName 要操作的角色组
     */
    public void canOperateRole(Long id, String roleName) {
        Role role = adminRoleService.getRoleByUserId(id);
        if (!role.getName().equals(roleName)) {
            throw new AccessDeniedException(null);
        }
    }
}
