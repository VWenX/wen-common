package io.github.vwenx.common.fieldfill.stuffer;

import io.github.vwenx.common.fieldfill.bean.FillCTX;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 批量值转换的填充器抽象
 * @param <S> 来源值类型
 * @param <T> 目标值类型
 *
 * @author XuWen
 */
public abstract class BatchModeStufferAbs<S, T> implements FieldFillStuffer{


    @Override
    public void fill(List<FillCTX> ctxList){
        // 取出source
        List<S> sourceValList = ctxList.stream().map(ctx -> (S) ctx.sourceValue).collect(Collectors.toList());
        // 转换
        Map<S, T> convertValMap = convert(sourceValList);
        // 赋值
        for (FillCTX ctx : ctxList) {
            ctx.setTargetVal(convertValMap.get((S) ctx.sourceValue));
        }
    }


    /**
     * 覆写实现转换逻辑
     * @param sourceValues 来源值
     * @return 来源值到转换结果的映射关系
     */
    protected abstract Map<S, T>  convert(Collection<S> sourceValues);



}
