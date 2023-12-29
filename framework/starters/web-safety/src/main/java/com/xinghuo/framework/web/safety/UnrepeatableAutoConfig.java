package com.xinghuo.framework.web.safety;

import com.xinghuo.framework.redis.RedisClient;
import com.xinghuo.framework.web.safety.repeat.UnRepeatableRequestAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author liao
 */
@Configuration
@ConditionalOnBean(RedisClient.class)
@Import({UnRepeatableRequestAspect.class})
public class UnrepeatableAutoConfig {
}
