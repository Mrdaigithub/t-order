package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.AdminChangeUserPasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserRegisterParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.tordermbg.model.Permission;
import club.mrdaisite.torder.tordermbg.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    User getAdminByUsername(String username);

    /**
     * 用户注册
     *
     * @param userRegisterParamDTO 注册参数
     * @return 返回注册的用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    UserResultDTO register(UserRegisterParamDTO userRegisterParamDTO);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取指定管理员分页列表
     *
     * @param page    指定第几页
     * @param perPage 每页的记录数
     * @param sortBy  指定返回结果按照哪个属性排序
     * @param order   排序顺序
     * @return 指定管理员分页列表
     */
    Object listAdmin(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 管理员修改用户密码
     *
     * @param id                              用户id
     * @param adminChangeUserPasswordParamDTO 新旧密码参数
     * @return
     * @throws CustomException
     */
    Object changeUserPassword(Long id, AdminChangeUserPasswordParamDTO adminChangeUserPasswordParamDTO) throws CustomException;

    /**
     * 获取指定用户的权限列表
     *
     * @param userId 用户id
     * @return 指定用户的权限列表
     */
    List<Permission> getPermissionList(Long userId);
}
