package club.mrdaisite.torder.tordermbg.mapper;

import club.mrdaisite.torder.tordermbg.model.OrderUserRelation;
import club.mrdaisite.torder.tordermbg.model.OrderUserRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderUserRelationMapper {
    long countByExample(OrderUserRelationExample example);

    int deleteByExample(OrderUserRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderUserRelation record);

    int insertSelective(OrderUserRelation record);

    List<OrderUserRelation> selectByExample(OrderUserRelationExample example);

    OrderUserRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderUserRelation record, @Param("example") OrderUserRelationExample example);

    int updateByExample(@Param("record") OrderUserRelation record, @Param("example") OrderUserRelationExample example);

    int updateByPrimaryKeySelective(OrderUserRelation record);

    int updateByPrimaryKey(OrderUserRelation record);
}