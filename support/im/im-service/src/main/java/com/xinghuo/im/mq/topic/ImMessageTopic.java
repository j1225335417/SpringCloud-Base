package com.xinghuo.im.mq.topic;

import com.xinghuo.framework.common.mq.MqTopic;
import com.xinghuo.framework.common.mq.annotation.Topic;
import com.xinghuo.im.mq.model.ImMqMessage;

/**
 * @author zhou miao
 * @date 2022/04/26
 */
@Topic("IM-MESSAGE")
public interface ImMessageTopic extends MqTopic<ImMqMessage> {

}
