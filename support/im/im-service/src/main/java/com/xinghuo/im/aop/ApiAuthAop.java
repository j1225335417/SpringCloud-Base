package com.xinghuo.im.aop;

import com.xinghuo.framework.common.exception.BizException;
import com.xinghuo.framework.web.util.RequestUtil;
import com.xinghuo.im.api.enums.TicketTypeEnum;
import com.xinghuo.im.api.result.ImApiResultEnum;
import com.xinghuo.im.context.ApiContext;
import com.xinghuo.im.model.entity.ThirdSystemConfig;
import com.xinghuo.im.service.InvalidTicketService;
import com.xinghuo.im.service.ThirdSystemConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.xinghuo.im.api.constant.ApiConstant.*;

/**
 * @author zhou miao
 * @date 2022/06/02
 */
@Aspect
@Component
@Slf4j
public class ApiAuthAop {
    @Resource
    private ThirdSystemConfigService thirdSystemConfigService;
    @Resource
    private InvalidTicketService invalidTicketService;

    @Pointcut("execution(public * com.xinghuo.im.controller.api.*.*(..))")
    public void around(){
    }

    @Around("around()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) {
        check();
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            throw new BizException(ImApiResultEnum.API_CURL_FAIL.getCode(), ImApiResultEnum.API_CURL_FAIL.getMessage());
        } finally {
            ApiContext.removeSystem();
            ApiContext.removeFrom();
        }
    }

    private void check() {
        String client = RequestUtil.getHeader(client_header);
        String ticket = RequestUtil.getHeader(ticket_header);
        String from = RequestUtil.getHeader(from_header);
        checkHeaderParam(client, ticket, from);
        checkTicketAndSetSystem(client, ticket, from);
    }

    private void checkTicketAndSetSystem(String client, String ticket, String from) {
        ThirdSystemConfig systemConfig = thirdSystemConfigService.findByClient(client);
        if (systemConfig == null) {
            throw new BizException(ImApiResultEnum.API_CURL_SYSTEM_SYSTEM_NOT_EXIST.getCode(), ImApiResultEnum.API_CURL_SYSTEM_SYSTEM_NOT_EXIST.getMessage());
        }
        boolean isUse = invalidTicketService.checkIsUseAndUpdateTicket(systemConfig, ticket, from, TicketTypeEnum.API_CURL_TICKET);
        if (isUse) {
            throw new BizException(ImApiResultEnum.API_CURL_TICKET_INVALID.getCode(), ImApiResultEnum.API_CURL_TICKET_INVALID.getMessage());
        }
        ApiContext.setSystem(systemConfig);
        ApiContext.setFrom(from);
    }

    private void checkHeaderParam(String client, String ticket, String from) {
        if (StringUtils.isEmpty(client) || StringUtils.isEmpty(ticket) || StringUtils.isEmpty(from)) {
            throw new BizException(ImApiResultEnum.API_CURL_PARAM_ERROR.getCode(), ImApiResultEnum.API_CURL_PARAM_ERROR.getMessage());
        }
    }
}
