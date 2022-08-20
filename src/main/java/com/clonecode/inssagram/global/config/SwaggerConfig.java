package com.clonecode.inssagram.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    //스웨거 REST API DOC 페이지에 안내될 설명 설정.
    private static final String API_NAME = "Inssagram CloneCode 프로젝트";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Inssagram BackEnd API Server";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .termsOfServiceUrl("")
//                .contact()
                .license("")
                .licenseUrl("")
                .build();
    }

    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()//apis, paths를 사용하주기 위한 builder
                .apis(RequestHandlerSelectors.basePackage("com.clonecode.inssagram.controller")) // 특정 패키지경로를 API문서화 한다. 1차 필터
                .paths(PathSelectors.any()) // apis중에서 특정 path조건 API만 문서화 하는 2차 필터
                .build()
                .groupName("API 0.0.1")//빈설정에 대한 그루핑을 한 그룹에 대한 구분자 값 설정.
                .apiInfo(this.apiInfo())//스웨거 설명
                .useDefaultResponseMessages(false); // 400,404,500 .. 표기를 ui에서 삭제한다.
    }

    @Override //swagger-ui.html 찾을 수 없다는 오류 발생시 추가.
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
