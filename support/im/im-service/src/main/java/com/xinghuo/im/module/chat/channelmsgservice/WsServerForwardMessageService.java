package com.xinghuo.im.module.chat.channelmsgservice;

import com.xinghuo.framework.common.model.IDict;
import com.xinghuo.im.model.vo.MessageVO;
import com.xinghuo.im.module.chat.channel.UserChannelManager;
import com.xinghuo.im.module.chat.model.RspMessage;
import com.xinghuo.im.module.waiter.channel.StoreWaiterChannelManager;
import com.xinghuo.im.netty.AbstractClientMessageService;
import com.xinghuo.im.netty.ConnectTypeEnum;
import com.xinghuo.im.portobuf.RspMessageProto;
import com.xinghuo.im.util.RspFrameUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 处理服务器消息转发处理类
 *
 * @author zhou miao
 * @date 2022/05/12
 */
@Service
@Slf4j
public class WsServerForwardMessageService extends AbstractClientMessageService<MessageVO> {

    @Resource
    private UserChannelManager userChannelManager;
    @Resource
    private StoreWaiterChannelManager storeWaiterChannelManager;

    @Override
    protected boolean handleError(ChannelHandlerContext ctx, String traceId, Integer traceType, MessageVO messageVO) {
        Integer originTraceType = messageVO.getOriginTraceType();
        String originTraceId = messageVO.getOriginTraceId();

        ConnectTypeEnum connectTypeEnum = IDict.getByCode(ConnectTypeEnum.class, messageVO.getConnectCode());
        RspMessageProto.Model message;
        switch (connectTypeEnum) {
            case COMMON:
                 message = RspFrameUtil.createModel(originTraceId, originTraceType, RspMessage.SUCCESS, null, messageVO);
                userChannelManager.pushMessage(messageVO.getSession().getSysId(), messageVO.getToId(), message);
                break;
            case WAITER:
                message = RspFrameUtil.createModel(originTraceId, originTraceType, RspMessage.SUCCESS, null, messageVO);
                storeWaiterChannelManager.pushMessage(messageVO.getSession().getSysId(), messageVO.getStoreId(), messageVO.getToId(), message);
                break;
            default:
                log.warn("消息错误 {}", messageVO);
        }
        return false;
    }

    @Override
    protected boolean paramError(MessageVO messageVO) {
        return false;
    }
}
