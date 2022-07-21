package cn.springlet.crypt.json;

import cn.springlet.crypt.annotation.JsonCipherText;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

import java.util.Iterator;

/**
 * json deserial modifier
 *
 * @author watermelon
 * @time 2020/9/24
 */
public class JsonDecryptDeSerializerModifier extends BeanDeserializerModifier {

    @Override
    public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc, BeanDeserializerBuilder builder) {
        Iterator<SettableBeanProperty> beanPropertyIterator = builder.getProperties();
        beanPropertyIterator.forEachRemaining(settableBeanProperty -> {
            JsonCipherText annotation = settableBeanProperty.getAnnotation(JsonCipherText.class);
            if (annotation != null && settableBeanProperty.getType().isTypeOrSubTypeOf(String.class)) {
                final SettableBeanProperty newSettableBeanProperty =
                        settableBeanProperty.withValueDeserializer(new JsonDecryptDeserializer(annotation));
                builder.addOrReplaceProperty(newSettableBeanProperty, true);
            }
        });

        return builder;
    }
}
