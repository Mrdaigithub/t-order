package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.UserInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.UserResultDTO;
import club.mrdaisite.torder.torderadmin.dto.UserUpdateParamDTO;
import club.mrdaisite.torder.tordermbg.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * AdminUserService
 *
 * @author dai
 * @date 2019/04/22
 */
public interface AdminUserService {
    /**
     * 获取用户分页列表
     *
     * @param page    指定第几页
     * @param perPage 每页的记录数
     * @param sortBy  指定返回结果按照哪个属性排序
     * @param order   排序顺序
     * @return 用户分页列表
     */
    List<Object> listUser(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<User> listUser();

    /**
     * 根据id获取后台管理员
     *
     * @param id 管理员id
     * @return 指定后台管理员
     * @throws CustomException 用户不存在异常
     */
    UserResultDTO getUserById(Long id) throws CustomException;

    /**
     * 根据用户名获取后台管理员
     *
     * @param username 用户名
     * @return 指定后台管理员
     */
    User getUserByUsername(String username);

    /**
     * 添加用户
     *
     * @param userInsertParamDTO 用户参数
     * @return 返回添加的用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    UserResultDTO insertUser(UserInsertParamDTO userInsertParamDTO);

    /**
     * 修改用户信息
     *
     * @param id                 用户id
     * @param userUpdateParamDTO 修改后的用户参数
     * @return 修改后的用户信息
     * @throws AccessDeniedException     用户不存在异常
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    UserResultDTO updateUser(Long id, UserUpdateParamDTO userUpdateParamDTO) throws AccessDeniedException, CustomException;

    /**
     * 删除管理员
     *
     * @param id 用户id
     * @throws AccessDeniedException 用户不存在异常
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteUser(Long id);

    /**
     * 判断用户是否存在
     *
     * @param id 用户id
     * @return 用户是否存在
     */
    Boolean userExists(Long id);
}
