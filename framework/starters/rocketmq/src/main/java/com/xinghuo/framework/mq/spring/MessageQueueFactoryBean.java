package com.xinghuo.framework.mq.spring;

import org.springframework.beans.factory.FactoryBean;

import static com.xinghuo.framework.mq.producer.MessageQueueInvocationHandler.generateProxyClass;


/**
 * @author liao
 */
public class MessageQueueFactoryBean<T> implements FactoryBean<T> {

    public final Class<T> type;

    public MessageQueueFactoryBean(Class<T> type) {
        this.type = type;
    }


    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        return (T) generateProxyClass(type);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }
}
