package cn.springlet.core.config.fastjson;

import cn.springlet.core.enums.DatabaseEnum;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

public class DatabaseEnumDeserializer implements ObjectDeserializer {
    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        // 解析值为字符串
        String value = parser.parseObject(String.class);

        DatabaseEnum databaseEnum = DatabaseEnum.valueOf((Class) type, value);
        if (databaseEnum != null) {
            return (T) databaseEnum;

        }
        // 没有匹配到，可以抛出异常或者返回null
        return null;
    }

    @Override
    public int getFastMatchToken() {
        // 仅仅匹配字符串类型的值 TODO 看能否匹配其它的
        return JSONToken.LITERAL_STRING;
    }
}