package com.joebunny.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableWebMvc
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            // 只生成被Api这个注解注解过的接口
            //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))

            // 只生成被ApiOperation这个注解注解过的接口
            //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))

            // 生成所有API接口
            .apis(RequestHandlerSelectors.basePackage("com.joebunny.web"))
            .paths(PathSelectors.any()).build()
            .apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
        Contact contact = new Contact("Joe", "https://github.com/joebvnny", "joebvnny@163.com");
        return new ApiInfoBuilder()
            .title("JoeBunny平台API")
            .description("JoeBunny平台在线API文档，主要提供基础平台的所有功能实现接口。")
            .contact(contact)
            .version("1.0.0")
            .build();
    }

}