package it.com.demo;

import com.google.common.collect.Sets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DemoQuotesSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoQuotesSpringbootApplication.class, args);
    }

    @Bean
    public Docket swaggerconfiguration() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.quotes"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .protocols(Sets.newHashSet("HTTP"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Progetto CRUD con SpringBoot",
                "Progetto che utilizza le tecnologie: Maven, SpringBoot, Postman, Swagger",
                "V1",
                "NA terms of service url",
                "Alessio Ottaviani", "info.licence.url", "http://www.apache.org/licenses/LICENSE-2.0.html");
    }
}
/*
- fix get method to default to all or filter by input @RequestParam

Add Business logic

- add a search function to search quotes by keyword
- add a function that return all quotes ranked by word count
- add a function that returns all quotes ranked by date
- add a function that returns the closest birthday of an Author
- add a function that ranks authors by age

*/
