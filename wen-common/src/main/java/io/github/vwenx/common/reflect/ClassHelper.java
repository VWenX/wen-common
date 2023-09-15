package io.github.vwenx.common.reflect;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClassHelper {

    // 后续看是否有必要使用软引用
    private static final Map<Class<?>, List<Field>> classFieldsMap = new ConcurrentHashMap<>();


    public static List<Field> getField_All(Class<?> c){
        ArrayList<Field> fields = new ArrayList<>(getSelfField(c));

        while (c.getSuperclass() != null){
            c = c.getSuperclass();
            fields.addAll(getSelfField(c));
        }
        return fields;
    }


    private static List<Field> getSelfField(Class<?> c){
        List<Field> fields = classFieldsMap.get(c);
        if (fields != null) {
            return fields;
        }

        List<Field> declaredFields = Arrays.asList(c.getDeclaredFields());
        classFieldsMap.put(c, declaredFields);
        return declaredFields;
    }


}
