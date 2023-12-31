package com.xinghuo.im.mq.service;

import com.xinghuo.im.mq.model.ImMqMessage;

/**
 * @description:
 * @author: zhou miao
 * @create: 2022/05/06
 */
public abstract class AbstractImMqMessageHandleService {
    public abstract void handle2Db(ImMqMessage imMqMessage);
    public abstract void handle2Cache(ImMqMessage imMqMessage);
}
