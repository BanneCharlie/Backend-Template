package com.banne.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Knife4jConfig {
    // 配置Swagger2的Docket的Bean实例
    @Bean
    public Docket systemDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("管理端接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.banne.template.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    // todo 更改文档信息配置
    private static final String API_TILE="天穹项目";

    //文档信息配置
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_TILE)
                .description("天穹项目在线API文档")
                .version("1.0")
                .build();
    }
}
