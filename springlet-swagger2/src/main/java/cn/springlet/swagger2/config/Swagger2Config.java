package cn.springlet.swagger2.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * swaggerConfig
 * <p>
 * <p>
 *
 * @author watermelon
 * @time 2021/3/3
 */
@Configuration
@EnableSwagger2WebMvc
@ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class Swagger2Config {

    @Autowired
    private SwaggerProperties swagger2Properties;

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

    private static final String BASE_PATH = "/**";


    @Bean
    public Docket defaultApi2() {
        String title = swagger2Properties.getTitle();
        title = StrUtil.isBlank(title) ? "接口文档" : title;

        String description = swagger2Properties.getDescription();
        description = StrUtil.isBlank(description) ? "接口文档描述" : description;

        String name = swagger2Properties.getContact().getName();
        name = StrUtil.isBlank(name) ? "springlet" : name;

        String email = swagger2Properties.getContact().getEmail();
        email = StrUtil.isBlank(email) ? "zfquan91@foxmail.com" : email;

        String termsOfServiceUrl = swagger2Properties.getTermsOfServiceUrl();
        termsOfServiceUrl = StrUtil.isBlank(termsOfServiceUrl) ? "" : termsOfServiceUrl;

        String version = swagger2Properties.getVersion();
        version = StrUtil.isBlank(version) ? "1.0" : version;

        String host = swagger2Properties.getHost();
        host = StrUtil.isBlank(host) ? "" : host;


        // base-path处理
        if (swagger2Properties.getBasePath().isEmpty()) {
            swagger2Properties.getBasePath().add(BASE_PATH);
        }

        // exclude-path处理
        if (swagger2Properties.getExcludePath().isEmpty()) {
            swagger2Properties.setExcludePath(new ArrayList<>());
        }
        swagger2Properties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);

        ApiSelectorBuilder builder = new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Properties.isEnabled())
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(description)
                        .contact(new Contact(name, "", email))
                        .termsOfServiceUrl(termsOfServiceUrl)
                        .version(version)
                        .build())
                //分组名称
                .host(host)
                .select()
                .apis(RequestHandlerSelectors.any());
        //不显示错误的接口地址

        swagger2Properties.getBasePath().forEach(p -> builder.paths(PathSelectors.ant(p)));
        swagger2Properties.getExcludePath().forEach(p -> builder.paths(PathSelectors.ant(p).negate()));

        return builder.build()
                .pathMapping("/");
    }


}
