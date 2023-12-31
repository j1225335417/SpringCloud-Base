package com.xinghuo.im.module.waiter.channelmsgservice;

import com.xinghuo.im.convert.SessionConvert;
import com.xinghuo.im.model.entity.Session;
import com.xinghuo.im.model.vo.SessionVO;
import com.xinghuo.im.module.chat.model.RspMessage;
import com.xinghuo.im.module.waiter.model.channelmessage.StoreUserCreateSession;
import com.xinghuo.im.module.waiter.model.entity.StoreConfig;
import com.xinghuo.im.module.waiter.service.StoreConfigService;
import com.xinghuo.im.module.waiter.service.StoreService;
import com.xinghuo.im.netty.AbstractClientMessageService;
import com.xinghuo.im.util.RspFrameUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * channel中客服功能用户创建客服会话
 *
 * @author zhou miao
 * @date 2022/04/18
 */
@Service
@Slf4j
public class WsStoreUserCreateSessionService extends AbstractClientMessageService<StoreUserCreateSession> {
    @Resource
    private StoreService storeService;
    @Resource
    private SessionConvert sessionConvert;
    @Resource
    private StoreConfigService storeConfigService;

    @Override
    protected boolean handleError(ChannelHandlerContext ctx, String traceId, Integer traceType, StoreUserCreateSession storeUserCreateSession) {
        Integer sysId = getSysId(ctx);
        StoreConfig storeConfig = storeConfigService.findBySysId(sysId);
        if (storeConfig == null) {
            log.warn("无权限调用 {}", sysId);
            return true;
        }

        String userId = getUserId(ctx);
        Session storeSession = storeService.createStoreSession(userId, sysId, storeConfig.getId(), channelManager.get(ctx.channel()));

        SessionVO sessionVO = sessionConvert.toVo(storeSession);
        sessionVO.setFromNickname(storeConfig.getName());
        sessionVO.setFromAvatar(storeConfig.getAvatar());
        ctx.writeAndFlush(RspFrameUtil.createRspFrame(traceId, traceType, RspMessage.SUCCESS, null, sessionVO));
        return false;
    }

    /**
     * 参数校验
     *
     * @param storeUserCreateSession 用户创建客服会话
     */
    @Override
    public boolean paramError(StoreUserCreateSession storeUserCreateSession) {
        return false;
    }


}
