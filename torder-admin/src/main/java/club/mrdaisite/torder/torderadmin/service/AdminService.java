package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.UpdatePasswordParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.tordermbg.model.Permission;
import club.mrdaisite.torder.tordermbg.model.Role;
import club.mrdaisite.torder.tordermbg.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
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
     * @throws CustomException 用户不存在异常
     */
    UserResultDTO getUserById(Long id) throws CustomException;

    /**
     * 添加用户
     *
     * @param userInsertParamDTO 用户参数
     * @param roleName           用户组名
     * @return 返回添加的用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    UserResultDTO insertUser(UserInsertParamDTO userInsertParamDTO, String roleName);

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
     */
    Boolean updateUserPassword(Long id, UpdatePasswordParamDTO updatePasswordParamDTO, String roleName) throws AccessDeniedException;

    /**
     * 根据用户名获取后台管理员
     *
     * @param username 用户名
     * @return 指定后台管理员
     */
    User getUserByUsername(String username);

    /**
     * 根据用户名获取用户组
     *
     * @param username 用户名
     * @return 指定用户组
     */
    Role getRoleByUsername(String username);

    /**
     * 根据权限值获取权限
     *
     * @param permissionValue 权限值
     * @return 指定权限
     */
    Permission getPermissionByPermissionValue(String permissionValue);

    /**
     * 获取指定用户的权限列表
     *
     * @param username 用户名
     * @return 指定用户的权限列表
     */
    List<Permission> getPermissionListByUsername(String username);
}
