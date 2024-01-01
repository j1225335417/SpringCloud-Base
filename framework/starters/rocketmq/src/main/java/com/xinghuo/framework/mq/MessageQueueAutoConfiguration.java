package com.xinghuo.framework.mq;


import com.xinghuo.framework.mq.annotation.MessageQueueScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author liao
 */
@Configuration
@MessageQueueScan("com.xinghuo.**.mq.**")
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.xinghuo.framework.mq.config")
@ComponentScan("com.xinghuo.framework.mq.*")
public class MessageQueueAutoConfiguration {

}
