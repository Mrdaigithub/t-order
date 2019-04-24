package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.dto.AdminInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminResultDTO;
import club.mrdaisite.torder.torderadmin.dto.AdminUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.exception.CustomNotFoundException;
import club.mrdaisite.torder.tordermbg.model.Admin;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AdminAdminService
 *
 * @author dai
 * @date 2019/03/21
 */
public interface AdminAdminService {
    /**
     * 获取管理员分页列表
     *
     * @param page    指定第几页
     * @param perPage 每页的记录数
     * @param sortBy  指定返回结果按照哪个属性排序
     * @param order   排序顺序
     * @return 管理员分页列表
     */
    List<Object> listAdmin(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 根据角色组id获取管理员列表
     *
     * @param roleId roleId 角色组id
     * @return 管理员列表
     */
    List<Admin> listAdminByRoleId(Long roleId) throws CustomNotFoundException;

    /**
     * 根据id获取管理员
     *
     * @param id 管理员id
     * @return 指定后台管理员
     * @throws CustomNotFoundException
     */
    AdminResultDTO getAdminById(Long id) throws CustomNotFoundException;

    /**
     * 根据用户名获取后台管理员
     *
     * @param username 用户名
     * @return 指定后台管理员
     * @throws CustomNotFoundException
     */
    Admin getAdminByUsername(String username) throws CustomNotFoundException;

    /**
     * 添加管理员
     *
     * @param adminInsertParamDTO 管理员参数
     * @param roleName            角色组名
     * @return 返回添加的管理员信息
     */
    @Transactional(rollbackFor = Exception.class)
    AdminResultDTO insertAdmin(AdminInsertParamDTO adminInsertParamDTO, String roleName);

    /**
     * 修改管理员信息
     *
     * @param id                  管理员id
     * @param adminUpdateParamDTO 修改后的管理员参数
     * @return 修改后的管理员信息
     * @throws CustomNotFoundException
     */
    AdminResultDTO updateAdmin(Long id, AdminUpdateParamDTO adminUpdateParamDTO) throws CustomNotFoundException;

    /**
     * 删除管理员
     *
     * @param id 管理员id
     * @throws CustomNotFoundException 管理员不存在异常
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteAdmin(Long id) throws CustomNotFoundException;

    /**
     * 判断管理员是否存在
     *
     * @param id 管理员id
     * @return 管理员是否存在
     */
    Boolean adminExists(Long id);
}
