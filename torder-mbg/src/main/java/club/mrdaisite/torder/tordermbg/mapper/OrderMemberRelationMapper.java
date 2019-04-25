package club.mrdaisite.torder.tordermbg.mapper;

import club.mrdaisite.torder.tordermbg.model.OrderMemberRelation;
import club.mrdaisite.torder.tordermbg.model.OrderMemberRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMemberRelationMapper {
    long countByExample(OrderMemberRelationExample example);

    int deleteByExample(OrderMemberRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderMemberRelation record);

    int insertSelective(OrderMemberRelation record);

    List<OrderMemberRelation> selectByExample(OrderMemberRelationExample example);

    OrderMemberRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderMemberRelation record, @Param("example") OrderMemberRelationExample example);

    int updateByExample(@Param("record") OrderMemberRelation record, @Param("example") OrderMemberRelationExample example);

    int updateByPrimaryKeySelective(OrderMemberRelation record);

    int updateByPrimaryKey(OrderMemberRelation record);
}