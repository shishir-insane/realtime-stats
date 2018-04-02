package com.sk.sales.stats.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.sk.sales.stats.aop.TimedLog;
import com.sk.sales.stats.data.TransactionDataStore;
import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

    private final TransactionDataStore transactionDataStore;

    public StatsServiceImpl() {
        transactionDataStore = TransactionDataStore.getInstance();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sk.sales.stats.service.StatsService#updateSalesData(double)
     */
    @Override
    @TimedLog
    public void updateSalesData(final double salesAmount, final Date saleTimestamp) {
        transactionDataStore.addSalesData(salesAmount, saleTimestamp);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sk.sales.stats.service.StatsService#getRealTimeStats()
     */
    @Override
    @TimedLog
    public StatsResponse getRealTimeStats() {
        StatsResponse response = transactionDataStore.getSalesStatsInDataStore();
        if (null == response) {
            response = new StatsResponse();
        }
        return response;
    }

}
