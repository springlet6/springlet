package cn.springlet.fast.config;

import cn.springlet.crypt.json.JsonDecryptDeSerializerModifier;
import cn.springlet.crypt.json.JsonEncryptSerializerModifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hccake.ballcat.common.desensitize.json.JsonDesensitizeSerializerModifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

        //json加密解密
        simpleModule.setSerializerModifier(new JsonEncryptSerializerModifier());
        simpleModule.setDeserializerModifier(new JsonDecryptDeSerializerModifier());
        objectMapper.registerModule(simpleModule);

        //json脱敏
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new JsonDesensitizeSerializerModifier()));
        return objectMapper;
    }
}
