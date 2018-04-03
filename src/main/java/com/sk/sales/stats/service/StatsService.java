/**
 * StatsService.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.service;

import java.util.Date;

import com.sk.sales.stats.dto.StatsResponse;

public interface StatsService {

    /**
     * Update sales data.
     *
     * @param salesAmount
     *            the sales amount
     * @param saleTimestamp
     *            the sale timestamp
     */
    void updateSalesData(double salesAmount, Date saleTimestamp);

    /**
     * Gets the real time stats.
     *
     * @return the real time stats
     */
    StatsResponse getRealTimeStats();

    /**
     * Update real time stats.
     */
    void updateRealTimeStats();

}
