package com.sk.sales.stats.service;

import java.util.Date;

import com.sk.sales.stats.dto.StatsResponse;

public interface StatsService {

    void updateSalesData(double salesAmount, Date saleTimestamp);

    StatsResponse getRealTimeStats();

    void updateRealTimeStats();

}
