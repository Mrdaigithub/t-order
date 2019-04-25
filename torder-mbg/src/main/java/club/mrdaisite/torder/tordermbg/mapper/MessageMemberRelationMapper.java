package club.mrdaisite.torder.tordermbg.mapper;

import club.mrdaisite.torder.tordermbg.model.MessageMemberRelation;
import club.mrdaisite.torder.tordermbg.model.MessageMemberRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageMemberRelationMapper {
    long countByExample(MessageMemberRelationExample example);

    int deleteByExample(MessageMemberRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageMemberRelation record);

    int insertSelective(MessageMemberRelation record);

    List<MessageMemberRelation> selectByExample(MessageMemberRelationExample example);

    MessageMemberRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageMemberRelation record, @Param("example") MessageMemberRelationExample example);

    int updateByExample(@Param("record") MessageMemberRelation record, @Param("example") MessageMemberRelationExample example);

    int updateByPrimaryKeySelective(MessageMemberRelation record);

    int updateByPrimaryKey(MessageMemberRelation record);
}