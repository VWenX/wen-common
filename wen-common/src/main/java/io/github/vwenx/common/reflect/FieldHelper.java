package io.github.vwenx.common.reflect;

import io.github.vwenx.common.exceptionhandle.SimpleCatch;

import java.lang.reflect.Field;

public class FieldHelper {

    public static Object getVal(Field field, Object object){
        return SimpleCatch.getOrThrow(() -> {
            field.setAccessible(true);
            return field.get(object);
        });
    }

    public static void setVal(Field field, Object object, Object val){
        SimpleCatch.catchThrow(() -> {
            field.setAccessible(true);
            field.set(object, val);
        });
    }

}
