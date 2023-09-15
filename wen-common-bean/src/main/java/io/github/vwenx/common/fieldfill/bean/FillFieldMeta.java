package io.github.vwenx.common.fieldfill.bean;

import io.github.vwenx.common.fieldfill.annotation.FieldFill;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 填充字段的元信息
 *     此Class的实例对象对应一个具体的@FieldFill声明
 *
 * @author XuWen
 */
public class FillFieldMeta {


    public final FieldFill annotation;
    public final Field field;
    public final Field targetField;
    public final Function<Object, Object> sourceGetter;
    public final BiConsumer<Object, Object> targetSetter;

    public FillFieldMeta(FieldFill annotation, Field field, Field targetField, Function<Object, Object> sourceGetter, BiConsumer<Object, Object> targetSetter) {
        this.annotation = annotation;
        this.field = field;
        this.targetField = targetField;
        this.sourceGetter = sourceGetter;
        this.targetSetter = targetSetter;
    }

    @Override
    public String toString() {
        return "FillFieldMeta{" +
                "annotation=" + annotation.toString() +
                ", field=" + field +
                ", targetField=" + targetField +
                '}';
    }
}
