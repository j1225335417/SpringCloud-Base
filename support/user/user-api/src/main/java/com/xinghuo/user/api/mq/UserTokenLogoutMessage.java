package com.xinghuo.user.api.mq;

import com.xinghuo.framework.common.mq.MqTopic;
import com.xinghuo.framework.common.mq.annotation.Topic;
import com.xinghuo.user.api.mq.body.UserTokenLogoutMessageBody;

/**
 * 用户下线消息
 *
 * @author liao
 */
@Topic("user_token_logout")
public interface UserTokenLogoutMessage extends MqTopic<UserTokenLogoutMessageBody> {

}
