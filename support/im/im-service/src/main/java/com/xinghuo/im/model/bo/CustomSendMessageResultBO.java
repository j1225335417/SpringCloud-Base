package com.xinghuo.im.model.bo;

import com.xinghuo.im.model.entity.Message;
import com.xinghuo.im.model.entity.Session;
import lombok.Builder;
import lombok.Data;

/**
 * 发送消息返回结果BO
 *
 * @author zhou miao
 * @date 2022/04/18
 */
@Data
@Builder
public class CustomSendMessageResultBO {
    private Message message;
    private Session session;
}
