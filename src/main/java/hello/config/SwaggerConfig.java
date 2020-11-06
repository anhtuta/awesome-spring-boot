package hello.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("swagger")
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("hello"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(parameterList("", "tuzaku123"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Spring boot OAuth2 example", "Hehehe.", "Version 1.0.0",
                "Terms of service", ApiInfo.DEFAULT_CONTACT, "License of API", "API license URL",
                Collections.emptyList());
    }

    private List<Parameter> parameterList(String acessToken, String nonsense) {
        List<Parameter> list = new ArrayList<Parameter>();
        String defaultAccessTokenParam = "";

        if (acessToken != null && !"".equals(acessToken)) {
            defaultAccessTokenParam = "Bearer " + acessToken;
        }

        Parameter param1 = new ParameterBuilder()
                .name("Authorization")
                .defaultValue(defaultAccessTokenParam)
                .description("Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        Parameter param2 = new ParameterBuilder()
                .name("nonsense")
                .defaultValue(nonsense)
                .description("Nonsense string")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        list.add(param1);
        list.add(param2);
        return list;
    }
}