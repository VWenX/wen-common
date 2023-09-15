package io.github.vwenx.common.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ListToTreeConverter<E, R, ID> {

    Collection<E> list;
    Function<E, R> toNode;
    Function<E, ID> getParentId;
    Function<R, ID> getId;
    BiConsumer<R, List<R>> setChildren;

    ListToTreeConverter(Collection<E> list, Function<E, R> toNode) {
        this.list = list;
        this.toNode = toNode;
    }

    /**
     * 将源集合转换生成树结构
     * @param topParentId 最顶层父id值
     * @return 树
     */
    public List<R> gen(ID topParentId){

        // 按父级标记分组 并将子级转换为树实体
        Map<ID, List<R>> pListMap = list.stream().collect(Collectors.groupingBy(
                getParentId,
                Collectors.mapping(toNode, Collectors.toList())
        ));

        // 取顶树
        List<R> treeTop = pListMap.get(topParentId);
        if (treeTop == null){
            return new ArrayList<>(0);
        }

        // 填充树实体的子级
        for (List<R> trees : pListMap.values()) {
            for (R tree : trees) {
                List<R> children = pListMap.get(getId.apply(tree));
                if (children == null) children = new ArrayList<>(0);
                setChildren.accept(tree, children);
            }
        }

        // 取顶树
        return treeTop;
    }




}
