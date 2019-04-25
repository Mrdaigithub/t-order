package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.MessageInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.MessageUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminMemberService;
import club.mrdaisite.torder.torderadmin.service.AdminMessageService;
import club.mrdaisite.torder.tordermbg.mapper.MessageMapper;
import club.mrdaisite.torder.tordermbg.mapper.MessageMemberRelationMapper;
import club.mrdaisite.torder.tordermbg.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dai
 * @date 2019/04/18
 */
@Service
public class AdminMessageServiceImpl implements AdminMessageService {
    @Autowired
    private AdminMemberService adminMemberService;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MessageMemberRelationMapper messageMemberRelationMapper;

    @Override
    public List<Object> listMessage(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Message> messageList = messageMapper.selectByExample(new MessageExample());
        PageInfo pageInfo = new PageInfo<>(messageList);
        return pageInfo.getList();
    }

    @Override
    public Message getMessageById(Long id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    @Override
    public Message insertMessage(MessageInsertParamDTO messageInsertParamDTO, Long userId) {
        Message message = new Message();
        BeanUtils.copyProperties(messageInsertParamDTO, message);
        message.setBroadcasterId(userId);
        message.setEnabled(true);
        message.setGmtCreate(new Date());
        message.setGmtModified(new Date());
        messageMapper.insert(message);
        Long messageId = message.getId();

        List<Member> memberList = adminMemberService.listMember();
        for (Member member : memberList) {
            MessageMemberRelation messageMemberRelation = new MessageMemberRelation();
            messageMemberRelation.setMessageId(messageId);
            messageMemberRelation.setMemberId(member.getId());
            messageMemberRelation.setHaveRead(false);
            messageMemberRelation.setGmtCreate(new Date());
            messageMemberRelation.setGmtModified(new Date());
            messageMemberRelationMapper.insert(messageMemberRelation);
        }
        return message;
    }

    @Override
    public Message updateMessage(Long id, MessageUpdateParamDTO messageUpdateParamDTO) {
        Message message = messageMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(messageUpdateParamDTO, message);
        message.setGmtModified(new Date());
        messageMapper.updateByPrimaryKeySelective(message);
        return message;
    }

    @Override
    public void deleteMessage(Long id) {
        MessageMemberRelationExample messageMemberRelationExample = new MessageMemberRelationExample();
        messageMemberRelationExample.or().andMessageIdEqualTo(id);
        messageMemberRelationMapper.deleteByExample(messageMemberRelationExample);
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean messageExists(Long id) {
        return messageMapper.selectByPrimaryKey(id) != null;
    }
}
