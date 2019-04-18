package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.dto.RoleInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.RoleUpdateParamDTO;
import club.mrdaisite.torder.tordermbg.model.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AdminService
 *
 * @author dai
 * @date 2019/04/17
 */
public interface AdminRoleService {
    /**
     * 获取角色组分页列表
     *
     * @param page    指定第几页
     * @param perPage 每页的记录数
     * @param sortBy  指定返回结果按照哪个属性排序
     * @param order   排序顺序
     * @return 角色组分页列表
     */
    List<Object> listRole(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 根据id获取指定角色组
     *
     * @param id 角色组id
     * @return 指定角色组
     */
    Role getRoleById(Long id);

    /**
     * 根据用户id获取指定角色组
     *
     * @param id 用户id
     * @return 指定角色组
     */
    Role getRoleByUserId(Long id);

    /**
     * 添加角色组
     *
     * @param roleInsertParamDTO 角色组参数
     * @return 返回添加的角色组信息
     */
    Role insertRole(RoleInsertParamDTO roleInsertParamDTO);

    /**
     * 修改角色组信息
     *
     * @param id                 角色组id
     * @param roleUpdateParamDTO 修改后的角色组参数
     * @return 修改后的角色组信息
     */
    Role updateRole(Long id, RoleUpdateParamDTO roleUpdateParamDTO);

    /**
     * 删除角色组
     *
     * @param id 角色组id
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteRole(Long id);

    /**
     * 判断角色组是否存在
     *
     * @param id 角色组id
     * @return 角色组是否存在
     */
    Boolean roleExists(Long id);
}
