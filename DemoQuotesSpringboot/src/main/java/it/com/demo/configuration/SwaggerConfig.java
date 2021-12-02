package it.com.demo.configuration;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final ApiInfo DEFAULT_API_INFO = new
            ApiInfo(
                    "Progetto CRUD con SpringBoot",
            "Progetto che utilizza le tecnologie: Maven, SpringBoot, Postman, Swagger",
            "V1",
            "NA terms of service url",
            "Alessio Ottaviani",
            "info.licence.url",
            "http://www.apache.org/licenses/LICENSE-2.0.html"
            );

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("it.com.demo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(DEFAULT_API_INFO)
                .protocols(Sets.newHashSet("HTTP"));
    }
}
