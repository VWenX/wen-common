package io.github.vwenx.common.fieldfill.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明在字段上，表示对此字段的值进行扫描
 *   扫描字段类型支持：集合、Map(仅扫描映射值)、普通实体对象。
 *   不支持继承自集合、Map后扩展的内部属性。
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScanFieldFill {
}
