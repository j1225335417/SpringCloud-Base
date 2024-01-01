package com.xinghuo.im.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinghuo.im.api.dto.AddStoreWaiterDTO;
import com.xinghuo.im.api.dto.StoreWaiterDTO;
import com.xinghuo.im.module.waiter.model.entity.StoreWaiter;
import com.xinghuo.im.api.vo.StoreWaiterVO;
import org.mapstruct.Mapper;

/**
 *
 * @author zhoumiao
 * @date 2022-04-25 16:24:17
 */
@Mapper(componentModel = "spring")
public interface StoreWaiterConvert {
    StoreWaiterVO toVo(StoreWaiter storeWaiter);

    StoreWaiter toDo(AddStoreWaiterDTO addStoreWaiterDTO);

    StoreWaiter toDo(StoreWaiterDTO storeWaiterDTO);

    Page<StoreWaiterVO> toVo(Page<StoreWaiter> storeWaiterPage);
}
