package com.santander.birras.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class ApplicationSwaggerConfig {

    /**
     * http://localhost:8080/swagger-ui.html
     */
    @Bean
    public Docket ruleEngineApi(@Value("${info.app.name}") String appName,
                                @Value("${info.app.version}") String appVersion,
                                @Value("${info.app.description}") String appDescription) {

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo(appName, appVersion, appDescription))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.santander.birras.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(String appName, String appVersion, String appDescription) {
        return new ApiInfoBuilder()
                .title(appName)
                .version(appVersion)
                .description(appDescription)
                .build();
    }
}
