package com.xinghuo.im.convert;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinghuo.im.api.dto.UpdateStoreConfigDTO;
import com.xinghuo.im.module.waiter.model.dto.AddStoreConfigDTO;
import com.xinghuo.im.module.waiter.model.dto.StoreConfigDTO;
import com.xinghuo.im.module.waiter.model.entity.StoreConfig;
import com.xinghuo.im.module.waiter.model.vo.StoreConfigVO;
import org.mapstruct.Mapper;

/**
 *
 * @author zhoumiao
 * @date 2022-04-25 16:24:17
 */
@Mapper(componentModel = "spring")
public interface StoreConfigConvert {
    StoreConfig toDo(AddStoreConfigDTO addStoreConfigDTO);

    StoreConfig toDo(StoreConfigDTO storeWaiterDTO);

    Page<StoreConfigVO> toVo(Page<StoreConfig> storeConfigPage);

    StoreConfig toDo(UpdateStoreConfigDTO updateStoreConfigDTO);
}

