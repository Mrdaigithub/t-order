package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.tordermbg.model.Admin;

/**
 * AdminService
 *
 * @author dai
 * @date 2019/03/21
 */
public interface AdminService {
    /**
     * 根据用户名获取后台管理员
     * @param username 用户名
     * @return 指定后台管理员
     */
    Admin getAdminByUsername(String username);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);
}
