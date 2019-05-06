package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.common.api.CommonPage;
import club.mrdaisite.torder.common.exception.CustomNotFoundException;
import club.mrdaisite.torder.common.util.ErrorCodeUtils;
import club.mrdaisite.torder.torderadmin.dto.MemberResultDTO;
import club.mrdaisite.torder.torderadmin.dto.MemberUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminMemberService;
import club.mrdaisite.torder.torderadmin.util.FuncUtils;
import club.mrdaisite.torder.tordermbg.mapper.MemberMapper;
import club.mrdaisite.torder.tordermbg.mapper.MessageMemberRelationMapper;
import club.mrdaisite.torder.tordermbg.mapper.OrderMemberRelationMapper;
import club.mrdaisite.torder.tordermbg.model.Member;
import club.mrdaisite.torder.tordermbg.model.MemberExample;
import club.mrdaisite.torder.tordermbg.model.MessageMemberRelationExample;
import club.mrdaisite.torder.tordermbg.model.OrderMemberRelationExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author dai
 * @date 2019/03/21
 */
@Service
public class AdminMemberServiceImpl implements AdminMemberService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MessageMemberRelationMapper messageMemberRelationMapper;
    @Autowired
    private OrderMemberRelationMapper orderMemberRelationMapper;

    @Override
    public CommonPage listMember(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Member> memberList = memberMapper.selectByExample(new MemberExample());
        PageInfo pageInfo = new PageInfo<>(memberList);
        List<Object> pageInfoList = pageInfo.getList();
        List<Object> targetList = new ArrayList<>();
        for (int i = 0; i < pageInfoList.size(); i++) {
            targetList.add(new MemberResultDTO());
        }
        return new CommonPage(pageInfo, new FuncUtils().beanUtilsCopyListProperties(pageInfoList, targetList));
    }

    @Override
    public List<Member> listMember() {
        return memberMapper.selectByExample(new MemberExample());
    }

    @Override
    public Member getMemberById(Long id) throws CustomNotFoundException {
        Member member = memberMapper.selectByPrimaryKey(id);
        Optional.ofNullable(member)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4042000).getEMessage()));
        return member;
    }

    @Override
    public MemberResultDTO getMemberDtoById(Long id) throws CustomNotFoundException {
        MemberResultDTO memberResultDTO = new MemberResultDTO();
        Member member = memberMapper.selectByPrimaryKey(id);
        Optional.ofNullable(member)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4042000).getEMessage()));
        BeanUtils.copyProperties(member, memberResultDTO);
        return memberResultDTO;
    }

    @Override
    public Member getMemberByUsername(String username) throws CustomNotFoundException {
        MemberExample memberExample = new MemberExample();
        memberExample.or().andUsernameEqualTo(username);
        List<Member> memberList = memberMapper.selectByExample(memberExample);
        Optional.ofNullable(memberList)
                .filter(admins -> admins.size() > 0)
                .orElseThrow(() -> new CustomNotFoundException(new ErrorCodeUtils(4042000).getEMessage()));
        return memberList.get(0);
    }

    @Override
    public MemberResultDTO updateMember(Long id, MemberUpdateParamDTO memberUpdateParamDTO) throws CustomNotFoundException {
        Member member = getMemberById(id);
        BeanUtils.copyProperties(memberUpdateParamDTO, member);
        member.setPassword(member.getPassword() == null ? null : bCryptPasswordEncoder.encode(member.getPassword()));
        member.setPid(member.getPid() == null ? 0 : member.getPid());
        memberMapper.updateByPrimaryKeySelective(member);
        return getMemberDtoById(id);
    }

    @Override
    public void deleteMember(Long id) throws CustomNotFoundException {
        Member member = getMemberById(id);

        MessageMemberRelationExample messageMemberRelationExample = new MessageMemberRelationExample();
        messageMemberRelationExample.or().andMemberIdEqualTo(member.getId());
        messageMemberRelationMapper.deleteByExample(messageMemberRelationExample);

        OrderMemberRelationExample orderMemberRelationExample = new OrderMemberRelationExample();
        orderMemberRelationExample.or().andMemberIdEqualTo(member.getId());
        orderMemberRelationMapper.deleteByExample(orderMemberRelationExample);

        memberMapper.deleteByPrimaryKey(member.getId());
    }
}
