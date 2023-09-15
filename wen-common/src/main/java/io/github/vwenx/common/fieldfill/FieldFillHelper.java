package io.github.vwenx.common.fieldfill;

import io.github.vwenx.common.fieldfill.bean.FillCTX;
import io.github.vwenx.common.fieldfill.bean.FillFieldMeta;
import io.github.vwenx.common.fieldfill.stuffer.FieldFillStuffer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.vwenx.common.fieldfill.Parser.getAllFillMeta;
import static io.github.vwenx.common.fieldfill.Parser.getAllNeedScanField;
import static io.github.vwenx.common.reflect.FieldHelper.getVal;


/**
 * 字段填充助手
 *     调用fill方法即可
 *
 * @author XuWen
 */
public class FieldFillHelper {


    /**
     * 填充对象中的字段
     * @param object 待处理的对象
     */
    public static void fill(Object object){
        fill(toCollection(object));
    }

    /**
     * 填充集合元素对象中的字段
     * @param collection 待处理的对象集合
     */
    public static void fill(Collection<?> collection){
        // 收集待处理
        List<FillCTX> allFillTask = getAllFillTask(collection);
        if (allFillTask.isEmpty()) return;

        // 按处理类型分组，交由处理器批量处理
        Map<String, List<FillCTX>> taskGroup = allFillTask.stream()
                .collect(Collectors.groupingBy(ctx -> ctx.meta.annotation.type()));
        for (Map.Entry<String, List<FillCTX>> entry : taskGroup.entrySet()) {
            String type = entry.getKey();
            List<FillCTX> ctxList = entry.getValue();

            FieldFillStuffer stuffer = FieldFillStufferRegistry.get(type);
            if (stuffer == null) {
                throw new RuntimeException("注册表不存在对应的填充器: " + type);
            }

            stuffer.fill(ctxList);
        }
    }

    /**
     * 解析出所有的填充任务
     * @param collection 需处理的集合数据
     * @return 所有的填充任务上下文
     */
    private static List<FillCTX> getAllFillTask(Collection<?> collection){
        if (collection == null || collection.isEmpty()){
            return Collections.emptyList();
        }

        List<FillCTX> fillCTXList = new ArrayList<>();
        for (Object obj : collection) {
            if (obj == null) continue;
            Class<?> objClass = obj.getClass();
            // 解析填充字段元数据 生成待填充上下文
            List<FillFieldMeta> allFillMeta = getAllFillMeta(objClass);
            for (FillFieldMeta fieldMeta : allFillMeta) {
                // 检查是否需要跳过
                if (fieldMeta.annotation.skipIfTargetNonNull()) {
                    Object targetValue = getVal(fieldMeta.targetField, obj);
                    if (targetValue != null) continue;
                }
                Object sourceValue = fieldMeta.sourceGetter.apply(obj);
                fillCTXList.add(new FillCTX(fieldMeta, obj, sourceValue));
            }

            // 向下扫描需要填充的字段元数据
            List<Field> allNeedScanField = getAllNeedScanField(objClass);
            for (Field scanField : allNeedScanField) {
                Object scanObj = getVal(scanField, obj);
                if (scanObj == null) continue;
                List<FillCTX> scanAllFillTask = getAllFillTask(toCollection(scanObj));
                fillCTXList.addAll(scanAllFillTask);
            }

        }
        return fillCTXList;
    }

    // 统一处理到集合类型 便于统一解析
    private static Collection<?> toCollection(Object obj){
        if (obj == null) return Collections.emptyList();
        if (obj instanceof Collection) return (Collection<?>) obj;
        if (obj.getClass().isArray()) return Arrays.asList((Object[]) obj);
        if (obj instanceof Map) return ((Map<?, ?>) obj).values();
        return Collections.singletonList(obj);
    }


}
