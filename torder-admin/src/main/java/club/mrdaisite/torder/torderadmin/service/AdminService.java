package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.AdminChangeUserPasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminRegisterParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminResultDTO;
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
     *
     * @param username 用户名
     * @return 指定后台管理员
     */
    Admin getAdminByUsername(String username);

    /**
     * 用户注册
     *
     * @param adminRegisterParamDTO 注册参数
     * @return 返回注册的用户信息
     */
    AdminResultDTO register(AdminRegisterParamDTO adminRegisterParamDTO);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 管理员修改用户密码
     * @param id 用户id
     * @param adminChangeUserPasswordParamDTO 新旧密码参数
     * @return
     * @throws CustomException
     */
    Object changeUserPassword(Long id, AdminChangeUserPasswordParamDTO adminChangeUserPasswordParamDTO) throws CustomException;
}
