package club.mrdaisite.torder.tordermember.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * torder
 *
 * @author dai
 * @date 2019/04/28
 */
@Configuration
@MapperScan({"club.mrdaisite.torder.tordermbg.mapper", "club.mrdaisite.torder.torderadmin.dao"})
@EnableTransactionManagement
public class MyBatisConfig {
}
