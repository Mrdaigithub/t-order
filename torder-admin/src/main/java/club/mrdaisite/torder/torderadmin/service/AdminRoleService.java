package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.common.exception.CustomNotFoundException;
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
    List<Role> listRole(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 根据名称获取角色组列表
     *
     * @param roleName 角色组名称
     * @return 角色组列表
     */
    List<Role> listRoleByName(String roleName);

    /**
     * 根据id获取指定角色组
     *
     * @param id 角色组id
     * @return 指定角色组
     */
    Role getRoleById(Long id) throws CustomNotFoundException;

    /**
     * 根据管理员id获取指定角色组
     *
     * @param id 管理员id
     * @return 指定角色组
     */
    Role getRoleByAdminId(Long id) throws CustomNotFoundException;

    /**
     * 添加角色组
     *
     * @param roleInsertParamDTO 角色组参数
     * @return 返回添加的角色组信息
     */
    Role insertRole(RoleInsertParamDTO roleInsertParamDTO) throws CustomNotFoundException;

    /**
     * 修改角色组信息
     *
     * @param id                 角色组id
     * @param roleUpdateParamDTO 修改后的角色组参数
     * @return 修改后的角色组信息
     */
    Role updateRole(Long id, RoleUpdateParamDTO roleUpdateParamDTO) throws CustomNotFoundException;

    /**
     * 删除角色组
     *
     * @param id 角色组id
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteRole(Long id) throws CustomNotFoundException;
}
