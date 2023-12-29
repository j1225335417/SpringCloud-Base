package com.xinghuo.framework.web;

import com.xinghuo.framework.web.config.DateTimeConfig;
import com.xinghuo.framework.web.config.DateTimeConvertPrimaryConfig;
import com.xinghuo.framework.web.exception.GlobalExceptionHandler;
import com.xinghuo.framework.web.exception.SentinelBlockExceptionHandler;
import com.xinghuo.framework.web.result.InitializingAdviceDecorator;
import com.xinghuo.framework.web.swagger.SwaggerConfiguration;
import com.xinghuo.framework.web.swagger.SwaggerShortcutController;
import com.xinghuo.framework.web.transform.TransformConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author liao
 */
@Configuration
@ComponentScan(basePackages = "com.xinghuo.framework.web")
@Import({SwaggerConfiguration.class, InitializingAdviceDecorator.class,
        GlobalExceptionHandler.class, SentinelBlockExceptionHandler.class,
        DateTimeConfig.class, SwaggerShortcutController.class, TransformConfig.class, DateTimeConvertPrimaryConfig.class})
public class WebAutoConfig {

}
