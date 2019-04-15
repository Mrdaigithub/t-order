package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
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
     * 获取指定管理员分页列表
     *
     * @param page    指定第几页
     * @param perPage 每页的记录数
     * @param sortBy  指定返回结果按照哪个属性排序
     * @param order   排序顺序
     * @return 指定管理员分页列表
     */
    List<Object> listUser(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 根据用户名获取后台管理员
     *
     * @param id 管理员id
     * @return 指定后台管理员
     */
    User getUserById(Long id);

    /**
     * 添加用户
     *
     * @param userInsertParamDTO 用户参数
     * @param roleId             用户组id
     * @return 返回添加的用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    UserResultDTO insertUser(UserInsertParamDTO userInsertParamDTO, Long roleId);

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
     * @param id                     用户id
     * @param updatePasswordParamDTO 新旧密码参数
     * @return 密码是否修改
     */
    Boolean updateUserPassword(Long id, UpdatePasswordParamDTO updatePasswordParamDTO);

    /**
     * 根据用户名获取后台管理员
     *
     * @param username 用户名
     * @return 指定后台管理员
     */
    User getAdminByUsername(String username);

    /**
     * 获取指定用户的权限列表
     *
     * @param userId 用户id
     * @return 指定用户的权限列表
     */
    List<Permission> getPermissionList(Long userId);
}
