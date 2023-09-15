package io.github.vwenx.common.fieldfill;

import io.github.vwenx.common.exceptionhandle.SimpleCatch;
import io.github.vwenx.common.fieldfill.annotation.FieldFill;
import io.github.vwenx.common.fieldfill.annotation.ScanFieldFill;
import io.github.vwenx.common.fieldfill.bean.FillFieldMeta;
import io.github.vwenx.common.reflect.ClassHelper;
import io.github.vwenx.common.reflect.FieldHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 包内部使用的基础解析器
 *
 * @author XuWen
 */
class Parser {


    private static final Map<Class<?>, List<FillFieldMeta>> classFillCache = new ConcurrentHashMap<>();

    // 解析class有注解的字段 解析器
    static List<FillFieldMeta> getAllFillMeta(Class<?> c){
        List<FillFieldMeta> fieldInfos = classFillCache.get(c);
        if (fieldInfos != null){
            return fieldInfos;
        }

        List<Field> fieldAll = ClassHelper.getField_All(c);

        // 方便解析匹配
        Map<String, Field> nameFieldMap = fieldAll.stream().collect(
                Collectors.toMap(Field::getName, o -> o, (a, b) -> {
                    throw new RuntimeException("不支持重名字段");// 不考虑支持骚操作
                })
        );

        fieldInfos = new LinkedList<>();
        for (Field field : fieldAll) {
            FillFieldMeta fieldInfo = SimpleCatch.getOrThrow(() -> parse(c, field, nameFieldMap), "解析FieldFill信息异常");
            if (fieldInfo == null) continue;
            fieldInfos.add(fieldInfo);
        }

        classFillCache.put(c, fieldInfos);
        return fieldInfos;
    }

    // 解析class里需要向下扫描的字段
    static List<Field> getAllNeedScanField(Class<?> c){
        List<Field> fieldAll = ClassHelper.getField_All(c);
        return fieldAll.stream()
                .filter(field -> field.isAnnotationPresent(ScanFieldFill.class))
                .collect(Collectors.toList());
    }

    private static FillFieldMeta parse(Class<?> c, Field field, Map<String, Field> nameFieldMap) throws Throwable {
        FieldFill annotation = field.getAnnotation(FieldFill.class);
        if (annotation == null) return null;
        // 确定来源获取
        Function<Object, Object> sourceGetter;
        String source = annotation.source();
        // 如果是方法 先匹配方法
        if (source.endsWith("()")){
            Method method = c.getMethod(source.replace("()", ""));
            sourceGetter = obj -> SimpleCatch.getOrThrow(() -> method.invoke(obj));
        } else { // 是字段
            Field sourceField = source.isEmpty() ? field : nameFieldMap.get(source);
            if (sourceField==null) throw new RuntimeException("source定义字段不存在:"+source);
            sourceGetter = obj -> FieldHelper.getVal(sourceField, obj);
        }

        // 确定目标
        String to = annotation.to();
        Field targetField = to.isEmpty() ? field : nameFieldMap.get(to);
        if (targetField==null) throw new RuntimeException("to定义字段不存在:"+to);
        BiConsumer<Object, Object> targetSetter = (obj, val) ->  FieldHelper.setVal(field, obj, val);

        return new FillFieldMeta(annotation, field, targetField, sourceGetter, targetSetter);
    }




}
