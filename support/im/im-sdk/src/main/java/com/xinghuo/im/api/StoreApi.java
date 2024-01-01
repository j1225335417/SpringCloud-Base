package com.xinghuo.im.api;

import com.ejlchina.okhttps.HttpUtils;
import com.xinghuo.im.api.config.ApiProperties;
import com.xinghuo.im.api.dto.UpdateStoreConfigDTO;
import com.xinghuo.im.api.vo.ResultVO;

import java.util.Objects;

import static com.xinghuo.im.api.constant.ApiConstant.*;

/**
 * @author zhou miao
 * @date 2022/06/02
 */
public class StoreApi {

    private String client;

    public StoreApi(String client) {
        Objects.requireNonNull(client, "client 为空");
        this.client = client;
    }

    public ResultVO updateStoreData(UpdateStoreConfigDTO updateStoreConfigDTO, String ticket, String fromId) {
        return HttpUtils.sync(ApiProperties.getBaseUrl() + "/api/store")
                .addHeader(client_header, client)
                .addHeader(ticket_header, ticket)
                .addHeader(from_header, fromId)
                .bodyType(bodyType)
                .setBodyPara(updateStoreConfigDTO)
                .put()
                .getBody()
                .toBean(ResultVO.class);
    }


}
