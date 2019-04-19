package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.MessageInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.MessageUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminMessageService;
import club.mrdaisite.torder.torderadmin.service.AdminRoleService;
import club.mrdaisite.torder.torderadmin.service.AdminUserService;
import club.mrdaisite.torder.torderadmin.util.LoggerUtil;
import club.mrdaisite.torder.tordermbg.mapper.MessageMapper;
import club.mrdaisite.torder.tordermbg.mapper.MessageUserRelationMapper;
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
    private AdminRoleService adminRoleService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MessageUserRelationMapper messageUserRelationMapper;

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
    public Message insertMessage(MessageInsertParamDTO messageInsertParamDTO, Long userId) throws CustomException {
        Message message = new Message();
        BeanUtils.copyProperties(messageInsertParamDTO, message);
        message.setBroadcasterId(userId);
        message.setEnabled(true);
        message.setGmtCreate(new Date());
        message.setGmtModified(new Date());
        messageMapper.insert(message);
        Long messageId = message.getId();

        List<Role> roleList = adminRoleService.listRoleByName("user");
        if (roleList.size() <= 0) {
            throw new CustomException("不存在的角色组");
        }
        Role role = roleList.get(0);

        List<User> userList = adminUserService.listUserByRoleId(role.getId());
        LoggerUtil.logger.error(userList.toString());
        for (User user : userList) {
            MessageUserRelation messageUserRelation = new MessageUserRelation();
            messageUserRelation.setMessageId(messageId);
            messageUserRelation.setUserId(user.getId());
            messageUserRelation.setHaveRead(false);
            messageUserRelation.setGmtCreate(new Date());
            messageUserRelation.setGmtModified(new Date());
            messageUserRelationMapper.insert(messageUserRelation);
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
        MessageUserRelationExample messageUserRelationExample = new MessageUserRelationExample();
        messageUserRelationExample.or().andMessageIdEqualTo(id);
        messageUserRelationMapper.deleteByExample(messageUserRelationExample);
        messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean messageExists(Long id) {
        return messageMapper.selectByPrimaryKey(id) != null;
    }
}
