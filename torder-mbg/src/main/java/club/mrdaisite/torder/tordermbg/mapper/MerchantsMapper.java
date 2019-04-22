package club.mrdaisite.torder.tordermbg.mapper;

import club.mrdaisite.torder.tordermbg.model.Merchants;
import club.mrdaisite.torder.tordermbg.model.MerchantsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantsMapper {
    long countByExample(MerchantsExample example);

    int deleteByExample(MerchantsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Merchants record);

    int insertSelective(Merchants record);

    List<Merchants> selectByExample(MerchantsExample example);

    Merchants selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Merchants record, @Param("example") MerchantsExample example);

    int updateByExample(@Param("record") Merchants record, @Param("example") MerchantsExample example);

    int updateByPrimaryKeySelective(Merchants record);

    int updateByPrimaryKey(Merchants record);
}