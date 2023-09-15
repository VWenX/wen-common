package io.github.vwenx.common.fieldfill.annotation;

import io.github.vwenx.common.annotation.Note;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供基于数据库表的参数约定.
 *   表查询逻辑需自己实现，可参阅DBTableStufferBuild
 *
 * @author XuWen
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTableArg {

    @Note("表名")
    String table();

    @Note("来源值在表中字段名")
    String source();

    @Note("目标值在表中字段名")
    String to();


}
