package com.sk.sales.stats.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.sales.stats.util.AppUtils;

public class StatsResponse {

    private final double totalSalesAmount;
    private final double avgSalesPerOrder;

    public StatsResponse(final double totalSalesAmount, final double avgSalesPerOrder) {
        this.totalSalesAmount = totalSalesAmount;
        this.avgSalesPerOrder = avgSalesPerOrder;
    }

    @JsonProperty(AppUtils.RES_PARAM_TOTAL_SALES_AMT)
    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    @JsonProperty(AppUtils.RES_PARAM_AVG_AMT_PER_ORDER)
    public double getAvgSalesPerOrder() {
        return avgSalesPerOrder;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
