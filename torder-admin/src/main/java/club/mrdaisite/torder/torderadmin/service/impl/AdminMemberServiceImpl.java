package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.MemberResultDTO;
import club.mrdaisite.torder.torderadmin.dto.MemberUpdateParamDTO;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.torderadmin.service.AdminMemberService;
import club.mrdaisite.torder.common.util.ErrorCodeUtils;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.tordermbg.mapper.MemberMapper;
import club.mrdaisite.torder.tordermbg.model.Member;
import club.mrdaisite.torder.tordermbg.model.MemberExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dai
 * @date 2019/03/21
 */
@Service
public class AdminMemberServiceImpl implements AdminMemberService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Object> listMember(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Member> memberList = memberMapper.selectByExample(new MemberExample());
        PageInfo pageInfo = new PageInfo<>(memberList);
        List<Object> pageInfoList = pageInfo.getList();
        List<Object> targetList = new ArrayList<>();
        for (int i = 0; i < pageInfoList.size(); i++) {
            targetList.add(new MemberResultDTO());
        }
        return new FuncUtils().beanUtilsCopyListProperties(pageInfoList, targetList);
    }

    @Override
    public List<Member> listMember() {
        return memberMapper.selectByExample(new MemberExample());
    }

    @Override
    public MemberResultDTO getMemberById(Long id) throws CustomNotFoundException {
        MemberResultDTO memberResultDTO = new MemberResultDTO();
        Member member = memberMapper.selectByPrimaryKey(id);
        if (member == null) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4042000).getEMessage());
        }
        assert member != null;
        BeanUtils.copyProperties(member, memberResultDTO);
        return memberResultDTO;
    }

    @Override
    public Member getMemberByUsername(String username) throws CustomNotFoundException {
        MemberExample memberExample = new MemberExample();
        memberExample.or().andUsernameEqualTo(username);
        List<Member> adminList = memberMapper.selectByExample(memberExample);
        if (adminList == null || adminList.size() <= 0) {
            throw new CustomNotFoundException(new ErrorCodeUtils(4042000).getEMessage());
        }
        assert adminList != null;
        return adminList.get(0);
    }

    @Override
    public MemberResultDTO updateMember(Long id, MemberUpdateParamDTO memberUpdateParamDTO) throws CustomNotFoundException {
        Member member = memberMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(memberUpdateParamDTO, member);
        if (member.getPid() == null) {
            member.setPid(null);
        }
        if (member.getPassword() != null) {
            member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        }
        member.setGmtModified(new Date());
        memberMapper.updateByPrimaryKeySelective(member);
        return getMemberById(id);
    }

    @Override
    public void deleteMember(Long id) {
        memberMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean memberExists(Long id) {
        return memberMapper.selectByPrimaryKey(id) != null;
    }
}
