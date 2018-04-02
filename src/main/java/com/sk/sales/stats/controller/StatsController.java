package com.sk.sales.stats.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.util.AppUtils;

@RestController
public class StatsController {

    @RequestMapping(method = RequestMethod.POST, value = AppUtils.ENDPOINT_SALES)
    public void getSales(@RequestParam(AppUtils.REQ_PARAM_SALES_AMOUNT) final double salesAmount) {

    }

    @RequestMapping(method = RequestMethod.GET, value = AppUtils.ENDPOINT_STATS, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StatsResponse getRealTimeStats() {
        return new StatsResponse(1000000.00, 45.04);
    }

}
