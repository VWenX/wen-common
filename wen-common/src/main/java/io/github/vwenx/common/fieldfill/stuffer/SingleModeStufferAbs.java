package io.github.vwenx.common.fieldfill.stuffer;

import io.github.vwenx.common.fieldfill.bean.FillCTX;

import java.util.List;

/**
 * 仅支持单个单个转换的填充器抽象
 * @param <S> 来源值类型
 * @param <T> 目标值类型
 *
 * @author XuWen
 */
public abstract class SingleModeStufferAbs<S, T> implements FieldFillStuffer {

    @Override
    public void fill(List<FillCTX> ctxList){
        for (FillCTX ctx : ctxList) {
            ctx.setTargetVal(convert(ctx, (S) ctx.sourceValue));
        }
    }


    protected abstract T convert(FillCTX ctx, S sourceValue);

}
