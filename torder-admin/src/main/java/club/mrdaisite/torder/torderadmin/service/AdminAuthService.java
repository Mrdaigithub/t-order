package club.mrdaisite.torder.torderadmin.service;

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
}
