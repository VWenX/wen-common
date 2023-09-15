package io.github.vwenx.common.fieldfill.stuffer.builder;

import io.github.vwenx.common.fieldfill.stuffer.BatchModeStufferAbs;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;


/**
 * 快捷构建批量值转换模式的填充器
 * @param <S> 来源值类型
 * @param <T> 目标值类型
 *
 * @author XuWen
 */
public class BatchModeStufferBuilder<S, T> extends BatchModeStufferAbs<S, T> {

    private final Function<Collection<S>, Map<S, T>> convertImpl;

    /**
     * 构建批量值转换模式的填充器
     * @param convertImpl 转换逻辑实现
     * @return 填充器
     * @param <S> 来源值类型
     * @param <T> 目标值类型
     */
    public static <S, T> BatchModeStufferAbs<S, T> of(Function<Collection<S>, Map<S, T>> convertImpl){
        return new BatchModeStufferBuilder<>(convertImpl);
    }

    public BatchModeStufferBuilder(Function<Collection<S>, Map<S, T>> convertImpl) {
        this.convertImpl = convertImpl;
    }

    @Override
    protected Map<S, T> convert(Collection<S> sourceValues) {
        return convertImpl.apply(sourceValues);
    }
}
