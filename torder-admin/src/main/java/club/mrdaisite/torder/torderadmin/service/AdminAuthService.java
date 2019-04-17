package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import org.springframework.security.access.AccessDeniedException;

/**
 * AdminService
 *
 * @author dai
 * @date 2019/04/17
 */
public interface AdminAuthService {
    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 修改用户密码
     *
     * @param id                     用户id
     * @param updatePasswordParamDTO 新旧密码参数
     * @param roleName               修改的用户组
     * @return 密码是否修改
     * @throws AccessDeniedException 用户不存在异常
     */
    Boolean updateUserPassword(Long id, UpdatePasswordParamDTO updatePasswordParamDTO, String roleName) throws AccessDeniedException;
}
