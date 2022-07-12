package cn.springlet.core.auto.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hccake.ballcat.common.desensitize.json.JsonDesensitizeSerializerModifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * jackson 配置
 *
 * @author watermelon
 * @since 2022/7/8
 */
@Configuration
public class JacksonConfig {
    @Bean
    @SuppressWarnings("all")
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        SimpleModule simpleModule = new SimpleModule();
        //解决雪花算法 Long精度丢失问题 序列化时将 Long 序列化为 String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        JsonDesensitizeSerializerModifier modifier = new JsonDesensitizeSerializerModifier();
        //3.将自定义序列化构建器 注册进ObjectMapper TODO 可以时不时关注一下最新版本，脱敏处理
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(modifier));
        return objectMapper;
    }
}
