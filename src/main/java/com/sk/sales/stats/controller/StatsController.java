/**
 * StatsController.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.service.StatsService;
import com.sk.sales.stats.util.AppUtils;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    /**
     * Update sales.
     *
     * @param salesAmount
     *            the sales amount
     */
    @RequestMapping(method = RequestMethod.POST, value = AppUtils.ENDPOINT_SALES)
    public void updateSales(@RequestParam(AppUtils.REQ_PARAM_SALES_AMOUNT) final double salesAmount) {
        statsService.updateSalesData(salesAmount, new Date());
    }

    /**
     * Gets the real time stats.
     *
     * @return the real time stats
     */
    @RequestMapping(method = RequestMethod.GET, value = AppUtils.ENDPOINT_STATS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StatsResponse getRealTimeStats() {
        return statsService.getRealTimeStats();
    }

}
