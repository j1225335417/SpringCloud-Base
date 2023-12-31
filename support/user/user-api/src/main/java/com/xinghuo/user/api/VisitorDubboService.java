package com.xinghuo.user.api;

import com.xinghuo.user.api.vo.VisitorStatisticsByDayDO;

import java.time.LocalDate;
import java.util.List;

public interface VisitorDubboService {
    Integer getVisitorCount(LocalDate date);

    Integer getVisitorCountByStatistics(LocalDate beginDate, LocalDate endDate);

    List<VisitorStatisticsByDayDO> listVisitorStatisticsByDay(LocalDate beginDate, LocalDate endDate);
}
