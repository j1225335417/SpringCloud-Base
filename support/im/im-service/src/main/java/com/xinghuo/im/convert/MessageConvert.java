package com.xinghuo.im.convert;

import com.xinghuo.im.api.dto.SendMessageDTO;
import com.xinghuo.im.api.dto.SendMoreUserMessageDTO;
import com.xinghuo.im.model.bo.SendMessageBO;
import com.xinghuo.im.model.entity.Message;
import com.xinghuo.im.model.vo.MessageVO;
import org.mapstruct.Mapper;

/**
 *
 * @author zhou miao
 * @date 2022/04/21
 */
@Mapper(componentModel = "spring")
public interface MessageConvert {
    MessageVO toVo(Message message);
    SendMessageBO toBo(SendMessageDTO sendMessageDTO);
    SendMessageBO toBo(SendMoreUserMessageDTO sendMessage);
    SendMessageDTO toDto(SendMoreUserMessageDTO sendMessage);
}
