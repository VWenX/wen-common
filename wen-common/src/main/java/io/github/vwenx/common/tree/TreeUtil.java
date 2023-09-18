package io.github.vwenx.common.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 树结构处理工具
 *    集合-》树、树-》集合、树-》树
 *
 * @author XuWen
 */
public class TreeUtil {


    /**
     * 将集合数据转换到树结构
     *     此方法是引导方法，将返回转换器的构建器.
     *     完成生成需要继续链式调用：toTree().config().gen()
     * @param list 数据集合
     * @param toNode 集合元素到树节点的转换实现
     * @param idPidType id/parentId的类型(避免泛型约束不严谨导致匹配问题)
     * @return 转换器的构建器
     * @param <E> 源类型
     * @param <R> 树节点类型
     * @param <ID> id/parentId类型
     */
    public static <E, R, ID> ListToTreeConverterBuilder<E, R, ID> toTree(Collection<E> list, Function<E, R> toNode, Class<ID> idPidType) {
        return new ListToTreeConverterBuilder<>(list, toNode, idPidType);
    }



    /**
     * 树转列 注：节点仍保持引用
     * @param tree 树
     * @param childrenGetter 子级获取器
     * @param <T> 节点类型
     * @return 所有节点
     */
    public static <T> List<T> toList(Collection<T> tree, Function<T, Collection<T>> childrenGetter){
        if (tree == null){
            return new ArrayList<>(0);
        }

        // 结果集
        List<T> list = new ArrayList<>();
        // 待查栈 初始入顶层树节点
        LinkedList<T> needFindStack = new LinkedList<>(tree);

        while (! needFindStack.isEmpty()){
            T node = needFindStack.pop();
            if (node == null){
                continue;
            }

            // 加入结果集
            list.add(node);

            // 子级加入待查栈
            Collection<T> children = childrenGetter.apply(node);
            if (children != null){
                needFindStack.addAll(children);
            }
        }

        return list;
    }


    /**
     * 树类型转换
     * @param tree 原树
     * @param convert 原树节点到新树节点的转换器 无需处理子级
     * @param childrenGetter 原树的子级获取器
     * @param childrenSetter 新树的子级设置器
     * @param <T> 原树节点类型
     * @param <R> 新树节点类型
     * @return 新树
     */
    public static <T, R> List<R> convertTree(Collection<T> tree, Function<T, R> convert,
                                             Function<T, Collection<T>> childrenGetter,
                                             BiConsumer<R, List<R>> childrenSetter){
        if (tree == null){
            return null;
        }

        List<R> newTree = new ArrayList<>(tree.size());
        for (T node : tree) {
            // 转换
            R newNode = convert.apply(node);
            newTree.add(newNode);
            // 子级处理 TODO:暂时简单递归，后期待优化为栈式处理
            Collection<T> children = childrenGetter.apply(node);
            if (children != null){
                List<R> newChildren = convertTree(children, convert, childrenGetter, childrenSetter);
                childrenSetter.accept(newNode, newChildren);
            }
        }
        return newTree;
    }

}
