package club.mrdaisite.torder.torderadmin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatisConfig
 *
 * @author dai
 * @date 2019/04/10
 */
@Configuration
@MapperScan({"club.mrdaisite.torder.tordermbg.mapper", "club.mrdaisite.torder.torderadmin.dao"})
@EnableTransactionManagement
public class MyBatisConfig {
}
