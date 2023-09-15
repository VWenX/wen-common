package io.github.vwenx.common.fieldfill.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明在需要处理的字段上，以便被填充工具扫描解析
 * 注意：
 *  - 虽然支持同时定义source、to，但并不建议将此注解定义在无关字段上。
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldFill {

    /**
     * 定义转换器类型
     *   定义的字段必须存在
     *   建议业务系统使用常量定义
     * @return 转换器
     */
    String type();

    /**
     * 定义来源字段名/方法
     *   默认空,视为注解修饰的当前字段
     *   以空括号结尾，可指定调用无参数方法: "name()"
     * @return 来源
     */
    String source() default "";

    /**
     * 定义填充目标字段名
     *   定义的字段必须存在
     *   默认空,视为注解修饰的当前字段
     * @return 目标字段
     */
    String to() default "";

    /**
     * 默认不处理已经有值的目标字段
     * @return 如果目标字段值非null，是否跳过
     */
    boolean skipIfTargetNonNull() default true;

    /**
     * 提供自定义参数扩展
     *   由具体的填充器定义解析
     * @return 参数
     */
    String[] args() default {};

    /**
     * 提供自定义参数扩展
     *   由具体的填充器定义解析
     * @return 参数
     */
    Class<?>[] classArgs() default {};

    /**
     * 提供基于表映射的参数扩展
     *   由具体的填充器定义解析
     * @return 参数
     */
    DBTableArg tableArg() default @DBTableArg(table = "", source = "", to = "");


}
