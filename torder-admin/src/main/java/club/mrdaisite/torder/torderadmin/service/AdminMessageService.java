package club.mrdaisite.torder.torderadmin.service;

import club.mrdaisite.torder.torderadmin.component.CustomException;
import club.mrdaisite.torder.torderadmin.dto.MessageInsertParamDTO;
import club.mrdaisite.torder.torderadmin.dto.MessageUpdateParamDTO;
import club.mrdaisite.torder.tordermbg.model.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AdminMessageService
 *
 * @author dai
 * @date 2019/04/18
 */
public interface AdminMessageService {
    /**
     * 获取消息分页列表
     *
     * @param page    指定第几页
     * @param perPage 每页的记录数
     * @param sortBy  指定返回结果按照哪个属性排序
     * @param order   排序顺序
     * @return 消息分页列表
     */
    List<Object> listMessage(Integer page, Integer perPage, String sortBy, String order);

    /**
     * 根据id获取指定消息
     *
     * @param id 消息id
     * @return 指定消息
     */
    Message getMessageById(Long id);

    /**
     * 添加消息
     *
     * @param messageInsertParamDTO 消息参数
     * @param userId                发布者id
     * @return 返回添加的消息信息
     * @throws CustomException 资源不存在异常
     */
    @Transactional(rollbackFor = Exception.class)
    Message insertMessage(MessageInsertParamDTO messageInsertParamDTO, Long userId) throws CustomException;

    /**
     * 修改消息信息
     *
     * @param id                    消息id
     * @param messageUpdateParamDTO 修改后的消息参数
     * @return 修改后的消息信息
     */
    Message updateMessage(Long id, MessageUpdateParamDTO messageUpdateParamDTO);

    /**
     * 删除消息
     *
     * @param id 消息id
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteMessage(Long id);

    /**
     * 判断消息是否存在
     *
     * @param id 消息id
     * @return 消息是否存在
     */
    Boolean messageExists(Long id);
}
