package cn.springlet.core.bean;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * beanUtil
 *
 * @author watermelon
 * @time 2020/11/23
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    /**
     * list 对象 复制
     *
     * @param collection
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<?> collection, Class<T> targetType) {
        return copyToList(collection, targetType);
    }

    /**
     * list 对象 复制
     *
     * @param collection
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<?> collection, Class<T> targetType, Consumer<T> consumer) {
        if (null == collection) {
            return null;
        }
        if (collection.isEmpty()) {
            return new ArrayList<>(0);
        }
        return collection.stream().map((source) -> {
            final T target = ReflectUtil.newInstanceIfPossible(targetType);
            copyProperties(source, target, CopyOptions.create());
            consumer.accept(target);
            return target;
        }).collect(Collectors.toList());
    }

    /**
     * list 对象 复制
     *
     * @param collection
     * @param targetType
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyList(List<S> collection, Class<T> targetType, BiConsumer<S, T> consumer) {
        if (null == collection) {
            return null;
        }
        if (collection.isEmpty()) {
            return new ArrayList<>(0);
        }
        return collection.stream().map((source) -> {
            final T target = ReflectUtil.newInstanceIfPossible(targetType);
            copyProperties(source, target, CopyOptions.create());
            consumer.accept(source, target);
            return target;
        }).collect(Collectors.toList());
    }
}
