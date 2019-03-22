package club.mrdaisite.torderadmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dai
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Bean
    public Docket createRestApi() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(apiKey());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securitySchemes(schemeList)
                .select()
                .apis(RequestHandlerSelectors.basePackage("club.mrdaisite.torderadmin.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("T Order 后台系统")
                .description("T Order 后台模块")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, tokenHeader, "header");
    }
}
