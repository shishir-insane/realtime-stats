/**
 * StatsResponse.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.sales.stats.util.AppUtils;

public class StatsResponse {

    private final double totalSalesAmount;
    private final double avgSalesPerOrder;

    /**
     * Instantiates a new stats response.
     */
    public StatsResponse() {
        totalSalesAmount = 0.0;
        avgSalesPerOrder = 0.0;
    }

    /**
     * Instantiates a new stats response.
     *
     * @param totalSalesAmount
     *            the total sales amount
     * @param avgSalesPerOrder
     *            the avg sales per order
     */
    public StatsResponse(final double totalSalesAmount, final double avgSalesPerOrder) {
        this.totalSalesAmount = totalSalesAmount;
        this.avgSalesPerOrder = avgSalesPerOrder;
    }

    /**
     * Gets the total sales amount.
     *
     * @return the total sales amount
     */
    @JsonProperty(AppUtils.RES_PARAM_TOTAL_SALES_AMT)
    public double getTotalSalesAmount() {
        return BigDecimal.valueOf(totalSalesAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Gets the avg sales per order.
     *
     * @return the avg sales per order
     */
    @JsonProperty(AppUtils.RES_PARAM_AVG_AMT_PER_ORDER)
    public double getAvgSalesPerOrder() {
        return BigDecimal.valueOf(avgSalesPerOrder).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
