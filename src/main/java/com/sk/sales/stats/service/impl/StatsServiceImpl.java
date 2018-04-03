/**
 * StatsServiceImpl.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.service.impl;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sk.sales.stats.aop.TimedLog;
import com.sk.sales.stats.data.TransactionDataStore;
import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

    private final TransactionDataStore transactionDataStore;

    private StatsResponse stats;

    public StatsServiceImpl() {
        transactionDataStore = TransactionDataStore.getInstance();
        stats = new StatsResponse();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.sk.sales.stats.service.StatsService#updateSalesData(double)
     */
    @Override
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
        if (null == stats) {
            stats = new StatsResponse();
        }
        return stats;
    }

    /**
     * Update real time stats at every 100 ms.
     */
    @TimedLog
    @Override
    @Scheduled(fixedDelay = 100)
    public void updateRealTimeStats() {
        final StatsResponse latestStats = transactionDataStore.getSalesStatsInDataStore();
        this.stats = latestStats;
    }

}
