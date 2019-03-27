package club.mrdaisite.torder.tordermbg.mapper;

import club.mrdaisite.torder.tordermbg.model.MessageUserRelation;
import club.mrdaisite.torder.tordermbg.model.MessageUserRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageUserRelationMapper {
    long countByExample(MessageUserRelationExample example);

    int deleteByExample(MessageUserRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageUserRelation record);

    int insertSelective(MessageUserRelation record);

    List<MessageUserRelation> selectByExample(MessageUserRelationExample example);

    MessageUserRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageUserRelation record, @Param("example") MessageUserRelationExample example);

    int updateByExample(@Param("record") MessageUserRelation record, @Param("example") MessageUserRelationExample example);

    int updateByPrimaryKeySelective(MessageUserRelation record);

    int updateByPrimaryKey(MessageUserRelation record);
}