package io.github.vwenx.common.fieldfill.stuffer.builder;

import io.github.vwenx.common.fieldfill.annotation.DBTableArg;
import io.github.vwenx.common.fieldfill.bean.FillCTX;
import io.github.vwenx.common.fieldfill.stuffer.FieldFillStuffer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于数据库自定义表、字段查询转换的填充器构建<br>
 * 注意： 此Class只实现任务处理框架，查询实现请使用构造方法传入<br>
 * 示例：<br>
 *   假设要使用的查询实现为 demoService 实例下的 Map&lt;Long, String&gt; queryTextById(table, source, to, sourceValues)<br>
 *   FieldFillStufferRegistry.register(FieldFillType.Table, DBTableStufferBuild.of(demoService::queryTextById));<br>
 *
 * @author XuWen
 */
public final class DBTableStufferBuild implements FieldFillStuffer {

    private final QueryFunction<?, ?> queryFunction;

    private DBTableStufferBuild(QueryFunction<?, ?> queryFunction) {
        if (queryFunction == null){
            throw new RuntimeException("必须实现查询方法！");
        }
        this.queryFunction = queryFunction;
    }

    /**
     * 构造基于数据表的填充器
     * @param queryFunction 查询实现
     * @return 填充器实例
     */
    public static FieldFillStuffer of(QueryFunction<?, ?> queryFunction) {
        return new DBTableStufferBuild(queryFunction);
    }


    @Override
    public void fill(List<FillCTX> ctxList){
        // 按表配置分组 注解equals会对比成员
        Map<DBTableArg, List<FillCTX>> tableArgGroup = ctxList.stream()
                .collect(Collectors.groupingBy(ctx -> ctx.meta.annotation.tableArg()));

        for (Map.Entry<DBTableArg, List<FillCTX>> entry : tableArgGroup.entrySet()) {
            DBTableArg tableArg = entry.getKey();
            List<FillCTX> tasks = entry.getValue();
            // 转换
            List sourceValues = tasks.stream().map(ctx -> ctx.sourceValue).collect(Collectors.toList());
            Map<?, ?> sourceResMap = queryFunction.query(tableArg.table(), tableArg.source(), tableArg.to(), sourceValues);
            // 赋值
            for (FillCTX ctx : tasks) {
                ctx.setTargetVal(sourceResMap.get(ctx.sourceValue));
            }
        }

    }

    public static interface QueryFunction<S, T> {
        Map<S, T> query(String tableName, String sourceField, String toField, Collection<S> sourceValues);
    }

}
