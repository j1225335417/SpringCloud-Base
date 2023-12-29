package com.xinghuo.framework.web.transform.annotation;

import com.xinghuo.framework.common.model.IDict;
import com.xinghuo.framework.web.transform.transformer.EnumTransformer;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})

@Transform(transformer = EnumTransformer.class)
public @interface TransformEnum {

    @AliasFor(annotation = Transform.class)
    String from() default "";

    Class<? extends IDict<?>> value();

}
