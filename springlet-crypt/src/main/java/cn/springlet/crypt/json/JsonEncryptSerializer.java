package cn.springlet.crypt.json;

import cn.hutool.core.lang.Singleton;
import cn.springlet.core.exception.web_return.ParameterVerificationException;
import cn.springlet.core.util.StrUtil;
import cn.springlet.crypt.CryptStrategy;
import cn.springlet.crypt.annotation.JsonCipherText;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Jackson 加密处理序列化器
 *
 * @author watermelon
 * @time 2020/9/24
 */
@Slf4j
public class JsonEncryptSerializer extends JsonSerializer<Object> {

    private final JsonCipherText encrypt;


    public JsonEncryptSerializer(JsonCipherText encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers)
            throws IOException {
        if (value instanceof String) {
            String str = (String) value;
            if (encrypt == null) {
                jsonGenerator.writeString(str);
                return;
            }
            CryptStrategy cryptStrategy = Singleton.get(encrypt.strategy());
            String privateKey = encrypt.privateKey();
            String publicKey = encrypt.publicKey();
            try {
                if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
                    jsonGenerator.writeString(cryptStrategy.encrypt(str, privateKey, publicKey));
                } else {
                    jsonGenerator.writeString(cryptStrategy.encrypt(str));
                }
            } catch (Exception e) {
                log.error("参数加密失败", e);
                throw new ParameterVerificationException("参数加密失败");
            }
        }
    }

}
