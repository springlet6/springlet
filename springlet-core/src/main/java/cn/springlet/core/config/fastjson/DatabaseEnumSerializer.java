package cn.springlet.core.config.fastjson;

import cn.springlet.core.enums.DatabaseEnum;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class DatabaseEnumSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        // 强制把值转换为DatabaseEnum
        DatabaseEnum databaseEnum = (DatabaseEnum) object;
        // 序列化
        serializer.out.writeString(databaseEnum.getDataBaseKey().toString());
    }
}