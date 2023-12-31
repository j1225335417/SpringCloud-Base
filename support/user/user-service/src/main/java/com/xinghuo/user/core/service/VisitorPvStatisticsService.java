package com.xinghuo.user.core.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinghuo.framework.redis.RedisClient;
import com.xinghuo.user.core.mapper.VisitorPvStatisticsMapper;
import com.xinghuo.user.core.model.constant.RedisKeysConstants;
import com.xinghuo.user.core.model.entity.VisitorPvStatistics;
import com.xinghuo.user.core.service.convert.VisitorPvStatisticsConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * 访客pv统计表(VisitorPvStatistics)表服务实现类
 *
 * @author shumengjiao
 * @since 2022-06-07 19:40:45
 */
@Service("visitorPvStatisticsService")
public class VisitorPvStatisticsService extends ServiceImpl<VisitorPvStatisticsMapper, VisitorPvStatistics> {

    @Resource
    private RedisClient redisClient;
    @Resource
    private VisitorPvStatisticsMapper visitorPvStatisticsMapper;
    @Resource
    private VisitorPvStatisticsConvert visitorPvStatisticsConvert;

    public void statistics(LocalDate date) {
        // 获取该日期在redis中保存的数据值
        Integer size = redisClient.get(RedisKeysConstants.VISITOR_PV_KEY + date);
        size = size == null ? 0 : size;
        VisitorPvStatistics visitorPvStatistics = new VisitorPvStatistics().setRecordDate(date).setCount(size);
        VisitorPvStatistics one = this.lambdaQuery().eq(VisitorPvStatistics::getRecordDate, date).one();
        if (one != null) {
            visitorPvStatisticsMapper.deleteById(one.getId());
        }
        this.save(visitorPvStatistics);
    }
}

