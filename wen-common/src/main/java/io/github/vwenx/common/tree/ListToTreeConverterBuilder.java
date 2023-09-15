package io.github.vwenx.common.tree;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

// 使用中间对象，强制引导调用链
public class ListToTreeConverterBuilder<E, R, ID>{

    private final ListToTreeConverter<E, R, ID> converter;

    public ListToTreeConverterBuilder(Collection<E> list, Function<E, R> toNode, Class<ID> idPidType) {
        converter = new ListToTreeConverter<>(list, toNode);
    }

    public ListToTreeConverter<E, R, ID> config(Function<R, ID> getId, Function<E, ID> getParentId, BiConsumer<R, List<R>> setChildren){
        converter.getId = getId;
        converter.getParentId = getParentId;
        converter.setChildren = setChildren;
        return converter;
    }

}