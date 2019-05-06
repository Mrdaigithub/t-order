package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.common.api.CommonPage;
import club.mrdaisite.torder.torderadmin.dto.MemberResultDTO;
import club.mrdaisite.torder.torderadmin.dto.MemberUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.tordermbg.model.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AdminMemberService
 *
 * @author dai
 * @date 2019/04/22
 */
public interface AdminMemberService {
    /**
     * 获取用户分页列表
     *
     * @param page    指定第几页
     * @param perPage 每页的记录数
     * @param sortBy  指定返回结果按照哪个属性排序
     * @param order   排序顺序
     * @return 用户分页列表
     */
    CommonPage listMember(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    List<Member> listMember();

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return 指定用户
     * @throws CustomNotFoundException 用户不存在异常
     */
    Member getMemberById(Long id) throws CustomNotFoundException;

    /**
     * 根据id获取用户dto
     *
     * @param id 用户id
     * @return 指定用户dto
     * @throws CustomNotFoundException 用户不存在异常
     */
    MemberResultDTO getMemberDtoById(Long id) throws CustomNotFoundException;

    /**
     * 根据用户名获取后台用户
     *
     * @param username 用户名
     * @return 指定后台用户
     */
    Member getMemberByUsername(String username) throws CustomNotFoundException;

    /**
     * 修改用户信息
     *
     * @param id                   用户id
     * @param memberUpdateParamDTO 修改后的用户参数
     * @return 修改后的用户信息
     * @throws CustomNotFoundException 用户不存在异常
     */
    MemberResultDTO updateMember(Long id, MemberUpdateParamDTO memberUpdateParamDTO) throws CustomNotFoundException;

    /**
     * 删除用户
     *
     * @param id 用户id
     * @throws CustomNotFoundException 不存在的用户
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteMember(Long id) throws CustomNotFoundException;
}
