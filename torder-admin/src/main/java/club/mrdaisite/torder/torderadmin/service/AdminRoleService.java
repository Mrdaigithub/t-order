package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.tordermbg.model.Role;
import org.springframework.security.access.AccessDeniedException;

/**
 * AdminService
 *
 * @author dai
 * @date 2019/04/17
 */
public interface AdminRoleService {
    /**
     * 根据用户名获取用户组
     *
     * @param username 用户名
     * @return 指定用户组
     */
    Role getRoleByUsername(String username);
}
