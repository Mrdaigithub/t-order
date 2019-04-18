package club.mrdaisite.torder.torderadmin.service.impl;

import club.mrdaisite.torder.torderadmin.dto.MessageInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.MessageUpdateParamDTO;
import club.mrdaisite.torder.torderadmin.service.AdminMessageService;
import club.mrdaisite.torder.tordermbg.mapper.MessageMapper;
import club.mrdaisite.torder.tordermbg.mapper.MessageUserRelationMapper;
import club.mrdaisite.torder.tordermbg.model.Message;
import club.mrdaisite.torder.tordermbg.model.MessageExample;
import club.mrdaisite.torder.tordermbg.model.MessageUserRelationExample;
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
    private MessageMapper MessageMapper;
    @Autowired
    private MessageUserRelationMapper messageUserRelationMapper;

    @Override
    public List<Object> listMessage(Integer page, Integer perPage, String sortBy, String order) {
        PageHelper.startPage(page, perPage, sortBy + " " + order);
        List<Message> messageList = MessageMapper.selectByExample(new MessageExample());
        PageInfo pageInfo = new PageInfo<>(messageList);
        return pageInfo.getList();
    }

    @Override
    public Message getMessageById(Long id) {
        return MessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public Message insertMessage(MessageInsertParamDTO messageInsertParamDTO, Long userId) {
        Message message = new Message();
        BeanUtils.copyProperties(messageInsertParamDTO, message);
        message.setGmtCreate(new Date());
        message.setGmtModified(new Date());
        MessageMapper.insert(message);
        return message;
    }

    @Override
    public Message updateMessage(Long id, MessageUpdateParamDTO messageUpdateParamDTO) {
        Message message = MessageMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(messageUpdateParamDTO, message);
        message.setGmtModified(new Date());
        MessageMapper.updateByPrimaryKeySelective(message);
        return message;
    }

    @Override
    public void deleteMessage(Long id) {
        MessageUserRelationExample messageUserRelationExample = new MessageUserRelationExample();
        messageUserRelationExample.or().andMessageIdEqualTo(id);
        messageUserRelationMapper.deleteByExample(messageUserRelationExample);
        MessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean messageExists(Long id) {
        return MessageMapper.selectByPrimaryKey(id) != null;
    }
}
