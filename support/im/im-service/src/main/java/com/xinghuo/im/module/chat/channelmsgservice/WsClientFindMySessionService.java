package com.xinghuo.im.module.chat.channelmsgservice;

import com.xinghuo.im.api.enums.SessionTypeEnum;
import com.xinghuo.im.module.waiter.service.StoreService;
import com.xinghuo.im.util.RspFrameUtil;
import com.xinghuo.im.convert.MessageConvert;
import com.xinghuo.im.convert.SessionConvert;
import com.xinghuo.im.model.entity.Message;
import com.xinghuo.im.model.entity.Session;
import com.xinghuo.im.model.entity.SessionUser;
import com.xinghuo.im.model.vo.ListSessionVO;
import com.xinghuo.im.model.vo.MessageVO;
import com.xinghuo.im.model.vo.SessionVO;
import com.xinghuo.im.module.chat.model.FindMySession;
import com.xinghuo.im.module.chat.model.RspMessage;
import com.xinghuo.im.module.waiter.model.entity.StoreConfig;
import com.xinghuo.im.module.waiter.service.StoreConfigService;
import com.xinghuo.im.netty.AbstractClientMessageService;
import com.xinghuo.im.redis.SessionRedisService;
import com.xinghuo.im.service.MessageService;
import com.xinghuo.im.service.SessionService;
import com.xinghuo.im.service.SessionUserService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 查询我的会话的处理类
 *
 * @author zhou miao
 * @date 2022/05/12
 */
@Service
@Slf4j
public class WsClientFindMySessionService extends AbstractClientMessageService<FindMySession> {
    @Resource
    private SessionService sessionService;
    @Resource
    private SessionUserService sessionUserService;
    @Resource
    private SessionConvert sessionConvert;
    @Resource
    private MessageConvert messageConvert;
    @Resource
    private MessageService messageService;
    @Resource
    private StoreConfigService storeConfigService;
    @Resource
    private StoreService storeService;

    @Override
    protected boolean handleError(ChannelHandlerContext ctx, String traceId, Integer traceType, FindMySession findMySession) {
        boolean waiterConnect = channelManager.isWaiterConnect(ctx.channel());
        if (waiterConnect) {
            String waiterId = getStoreWaiterId(ctx);
            Integer systemId = getStoreSysId(ctx);
            Long storeId = getStoreId(ctx);

            ListSessionVO listSessionVO = storeService.findWaiterSessions(systemId, storeId, waiterId);

            ctx.writeAndFlush(RspFrameUtil.createRspFrame(traceId, traceType, RspMessage.SUCCESS, null, listSessionVO));
        } else {

            Integer sysId = getSysId(ctx);
            String userId = getUserId(ctx);
            int size = findMySession == null ? SessionRedisService.USER_SESSION_CACHE_SIZE : findMySession.getSize();
            List<Session> mySession = sessionService.findMySession(userId, sysId, size);
            if (mySession.isEmpty()) {
                ctx.writeAndFlush(RspFrameUtil.createRspFrame(traceId, traceType, RspMessage.SUCCESS, null, Collections.emptyList()));
                return false;
            }

            ListSessionVO listSessionVO = composeListSessionVO(sysId, userId, mySession);
            ctx.writeAndFlush(RspFrameUtil.createRspFrame(traceId, traceType, RspMessage.SUCCESS, null, listSessionVO));
        }
        return false;
    }

    private ListSessionVO composeListSessionVO(Integer sysId, String userId, List<Session> mySession) {
        List<SessionVO> sessionVOS = new ArrayList<>(mySession.size());
        Long totalUnreadCount = 0L;
        for (Session session : mySession) {
            SessionVO sessionVO = sessionConvert.toVo(session);

            // 最后一条消息
            Message messageLast = messageService.findSessionLastMessage(session.getId());
            if (messageLast != null) {
                MessageVO messageVO = messageConvert.toVo(messageLast);
                sessionVO.setLastMessage(messageVO);
            }

            if (Objects.equals(SessionTypeEnum.GROUP.getCode(), session.getType())) {

            } else if (Objects.equals(SessionTypeEnum.STORE.getCode(), session.getType())) {
                StoreConfig storeConfig = storeConfigService.findBySysId(sysId);
                sessionVO.setFromId(storeConfig.getId().toString());
                sessionVO.setFromNickname(storeConfig.getName());
                sessionVO.setFromAvatar(storeConfig.getAvatar());
            } else {
                SessionUser sessionUser = sessionUserService.findBySessionIdAndUserId(session.getId(), userId);
                String relationUserId = sessionUser.getRelationUserId();
                SessionUser relationUser = sessionUserService.findBySessionIdAndUserId(session.getId(), relationUserId);
                if (relationUser != null) {
                    sessionVO.setFromAvatar(relationUser.getUserAvatar());
                    sessionVO.setFromId(relationUserId);
                    sessionVO.setFromNickname(relationUser.getUserNickname());
                } else {
                    log.error("用户{}不存在", relationUserId);
                }
            }

            // 会话未读数
            Long unreadCount = sessionUserService.findUnreadCount(userId, session.getId());
            sessionVO.setUnreadCount(unreadCount);
            totalUnreadCount += unreadCount;
            sessionVOS.add(sessionVO);
        }

        ListSessionVO listSessionVO = new ListSessionVO();
        listSessionVO.setSessionVOS(sessionVOS);
        listSessionVO.setTotalUnreadCount(totalUnreadCount);
        return listSessionVO;
    }

    @Override
    protected boolean paramError(FindMySession findMySession) {
        return findMySession != null && findMySession.getSize() != null && findMySession.getSize() <= 0;
    }


}
