package io.github.vwenx.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author XuWen
 */
@Note("用于在编译包中保留注释")
@Retention(RetentionPolicy.CLASS)
public @interface Note {

    String[] value();

}
