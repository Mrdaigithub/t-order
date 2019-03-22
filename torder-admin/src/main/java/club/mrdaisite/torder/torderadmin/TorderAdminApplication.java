package club.mrdaisite.torder.torderadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * @author dai
 */
@SpringBootApplication
@MapperScan({"club.mrdaisite.torder.tordermbg.mapper"})
@EnableTransactionManagement
public class TorderAdminApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TorderAdminApplication.class, args);
    }

}
