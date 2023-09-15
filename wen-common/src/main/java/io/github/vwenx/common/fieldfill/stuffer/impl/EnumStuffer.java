package io.github.vwenx.common.fieldfill.stuffer.impl;

import io.github.vwenx.common.fieldfill.bean.FillCTX;
import io.github.vwenx.common.fieldfill.stuffer.SingleModeStufferAbs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static io.github.vwenx.common.exceptionhandle.SimpleCatch.getOrThrow;
import static io.github.vwenx.common.reflect.FieldHelper.getVal;

/**
 * 基于枚举映射转换的字段填充器实现。
 * 需要在注解中定义扩展参数：
 *   classArgs: 需要一个枚举类型。
 *   args: 定义一个转换方法，或两个映射属性。eg:
 *     {"getTextByCode()"} 定义方法时固定以空括号结尾，将会寻找[与sourceVal对象匹配(如不为null)的]单参数静态方法调用。
 *     {"code", "text"} 定义属性，则将匹配属性code与sourceVal相等的枚举对象，取其text属性填充。
 *
 * @author XuWen
 */
public class EnumStuffer extends SingleModeStufferAbs<Object, Object> {

    @Override
    protected Object convert(FillCTX ctx, Object sourceValue) {
        String[] args = ctx.meta.annotation.args();
        // 枚举类型单参数视为静态转换方法 ["getTextByCode()"]
        if (args.length == 1){
            return staticConvert(ctx);
        }

        // 双参数视为枚举属性映射 ["source", "target"]
        Class<?> c = ctx.meta.annotation.classArgs()[0];
        Field sourceField = getOrThrow(() -> c.getDeclaredField(args[0]), "未找到来源枚举属性 " + ctx);
        Field targetField = getOrThrow(() -> c.getDeclaredField(args[1]), "未找到目标枚举属性 " + ctx);
        Enum<?>[] enumConstants = (Enum<?>[]) c.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            if (sourceValue.equals(getVal(sourceField, enumConstant))){
                return getVal(targetField, enumConstant);
            }
        }
        return null;
    }

    private static Object staticConvert(FillCTX ctx){
        String methodName = ctx.meta.annotation.args()[0].replace("()", "");
        for (Method method : ctx.meta.annotation.classArgs()[0].getMethods()) {
            if (!Modifier.isStatic(method.getModifiers())) continue;
            if (!method.getName().equals(methodName)) continue;

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) continue;

            if (ctx.sourceValue==null
                    || parameterTypes[0].isPrimitive() // 基本类型先不验证,jdk没提供简洁获取包装类的内置方法
                    || parameterTypes[0].isInstance(ctx.sourceValue)
            ){
                return getOrThrow(()->method.invoke(null, ctx.sourceValue), "枚举转换方法调用异常:"+ctx);
            }

        }
        throw new RuntimeException("枚举转换未匹配到定义方法:"+ctx);
    }

}
