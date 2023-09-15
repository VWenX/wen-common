package io.github.vwenx.common.fieldfill.stuffer.builder;

import io.github.vwenx.common.fieldfill.bean.FillCTX;
import io.github.vwenx.common.fieldfill.stuffer.SingleModeStufferAbs;

import java.util.function.Function;

/**
 * 快捷构建单值转换模式的填充器
 * @param <S> 来源值类型
 * @param <T> 目标值类型
 *
 * @author XuWen
 */
public class SingleModeStufferBuild<S, T> extends SingleModeStufferAbs<S, T> {

    private final Function<S, T> convertImpl;

    /**
     * 构建单值转换模式的填充器
     * @param convertImpl 转换逻辑实现
     * @return 填充器
     * @param <S> 来源值类型
     * @param <T> 目标值类型
     */
    public static <S, T> SingleModeStufferAbs<S, T> of(Function<S, T> convertImpl){
        return new SingleModeStufferBuild<>(convertImpl);
    }

    protected SingleModeStufferBuild(Function<S, T> convertImpl) {
        this.convertImpl = convertImpl;
    }

    @Override
    protected T convert(FillCTX ctx, S sourceValue) {
        return convertImpl.apply(sourceValue);
    }


}
