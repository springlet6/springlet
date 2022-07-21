package cn.springlet.crypt.json;

import cn.hutool.core.lang.Singleton;
import cn.springlet.core.exception.web_return.ParameterVerificationException;
import cn.springlet.core.util.StrUtil;
import cn.springlet.crypt.CryptStrategy;
import cn.springlet.crypt.annotation.JsonCipherText;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Jackson解密处理序列化器
 *
 * @author watermelon
 * @time 2020/9/24
 */
@Slf4j
public class JsonDecryptDeserializer extends JsonDeserializer<String> {

    private final JsonCipherText decrypt;


    public JsonDecryptDeserializer(JsonCipherText decrypt) {
        this.decrypt = decrypt;
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String str = p.getValueAsString();
        if (decrypt == null) {
            return str;
        }
        CryptStrategy cryptStrategy = Singleton.get(decrypt.strategy());
        String privateKey = decrypt.privateKey();
        String publicKey = decrypt.publicKey();
        try {
            if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
                str = cryptStrategy.decrypt(str, privateKey, publicKey);
            } else {
                str = cryptStrategy.decrypt(str);
            }
        } catch (Exception e) {
            log.error("参数解密失败", e);
            throw new ParameterVerificationException("参数解密失败");
        }
        return str;
    }

    @Override
    public Class<?> handledType() {
        return String.class;
    }
}
