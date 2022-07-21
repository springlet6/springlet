package cn.springlet.crypt.json;

import cn.springlet.crypt.annotation.JsonCipherText;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;

/**
 * json serial modifier
 *
 * @author watermelon
 * @time 2020/9/24
 */
public class JsonEncryptSerializerModifier extends BeanSerializerModifier {


    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter beanProperty : beanProperties) {
            JsonCipherText annotation = beanProperty.getAnnotation(JsonCipherText.class);
            if (annotation != null && beanProperty.getType().isTypeOrSubTypeOf(String.class)) {
                beanProperty.assignSerializer(new JsonEncryptSerializer(annotation));
            }
        }
        return beanProperties;
    }
}
