package com.xinghuo.framework.mysql.shard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author liao
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface TableShardField {
}
