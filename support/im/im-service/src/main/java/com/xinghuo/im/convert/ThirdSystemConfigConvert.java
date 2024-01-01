package com.xinghuo.im.convert;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinghuo.im.model.dto.AddThirdSystemConfigDTO;
import com.xinghuo.im.model.entity.ThirdSystemConfig;
import com.xinghuo.im.model.vo.ThirdSystemConfigVO;
import com.xinghuo.im.model.dto.ThirdSystemConfigDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 *
 * @author zhoumiao
 * @date 2022-04-25 16:24:17
 */
@Mapper(componentModel = "spring")
public interface ThirdSystemConfigConvert {

    ThirdSystemConfigVO toVo(ThirdSystemConfig thirdSystemConfig);

    List<ThirdSystemConfigVO> toVo(List<ThirdSystemConfig> horseImThirdSIES);

    Page<ThirdSystemConfigVO> toVo(Page<ThirdSystemConfig> horseImThirdSys);

    ThirdSystemConfig toDo(ThirdSystemConfigDTO thirdSystemConfigDTO);

    ThirdSystemConfig toDo(AddThirdSystemConfigDTO addthirdSystemConfigDTO);
}

