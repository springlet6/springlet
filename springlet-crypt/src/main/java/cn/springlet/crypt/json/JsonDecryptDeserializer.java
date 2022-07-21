package cn.springlet.crypt.json;

import cn.hutool.core.lang.Singleton;
import cn.springlet.core.util.StrUtil;
import cn.springlet.crypt.CryptStrategy;
import cn.springlet.crypt.annotation.JsonCipherText;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Jackson解密处理序列化器
 *
 * @author watermelon
 * @time 2020/9/24
 */
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
        if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
            return cryptStrategy.decrypt(str, privateKey, publicKey);
        } else {
            return cryptStrategy.decrypt(str);
        }
    }

    @Override
    public Class<?> handledType() {
        return String.class;
    }
}
