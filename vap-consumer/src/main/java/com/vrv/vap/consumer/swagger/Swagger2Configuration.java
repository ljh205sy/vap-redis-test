package com.vrv.vap.consumer.swagger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author liujinhui
 * @date 2019年11月2日20:16:36
 */
@EnableSwagger2
@Configuration
@ConditionalOnProperty(name = "club.swagger.enabled", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class Swagger2Configuration {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnMissingBean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("资源管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vrv.vap"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(StringUtils.isEmpty(swaggerProperties.getTitle()) ? "打包应用程序" : "swaggerProperties.getTitle()")
                .description(StringUtils.isEmpty(swaggerProperties.getDescription()) ? "在线打包" : swaggerProperties.getDescription())
                .termsOfServiceUrl("http://localhost:10000")
                .contact(new Contact("liujh", "http://www.bing.com", "ljh205_sy@163.com"))
                .version("v1.0.0")
                .build();
    }

}


