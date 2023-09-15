package io.github.vwenx.common.fieldfill;

import io.github.vwenx.common.fieldfill.constant.FieldFillType;
import io.github.vwenx.common.fieldfill.stuffer.FieldFillStuffer;
import io.github.vwenx.common.fieldfill.stuffer.impl.EnumStuffer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 字段填充功能填充器注册表
 *   用以维护填充器实例
 *
 * @author XuWen
 */
public class FieldFillStufferRegistry {

    private static final ConcurrentHashMap<String, FieldFillStuffer> converterMap = new ConcurrentHashMap<>();

    static {
        // 初始化注册内置填充器
        register(FieldFillType.Enum, new EnumStuffer());
    }

    /**
     * 注册对应类型的填充器实例
     * @param type 类型
     * @param stuffer 填充器实例
     */
    public static void register(String type, FieldFillStuffer stuffer){
        converterMap.put(type, stuffer);
    }

    static FieldFillStuffer get(String type){
        return converterMap.get(type);
    }

}
