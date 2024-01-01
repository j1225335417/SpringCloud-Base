package com.xinghuo.im.convert;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinghuo.im.api.dto.UpdateSessionUserDTO;
import com.xinghuo.im.model.dto.SessionUserDTO;
import com.xinghuo.im.model.entity.SessionUser;
import com.xinghuo.im.model.vo.SessionUserVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 *
 * @author zhoumiao
 * @date 2022-04-13 16:29:34
 */
@Mapper(componentModel = "spring")
public interface SessionUserConvert {

    SessionUserVO toVo(SessionUser sessionUser);

    List<SessionUserVO> toVo(List<SessionUser> sessionUser);

    Page<SessionUserVO> toVo(Page<SessionUser> horseImSessionUser);

    SessionUser toDo(SessionUserDTO sessionUserDTO);

    SessionUser toDo(UpdateSessionUserDTO updateSessionUserDTO);
}

