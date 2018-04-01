package com.sk.sales.stats.data;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AggregatedSalesData {

    private double totalAmount;
    private long salesQty;
    private final Date dataTimeStamp;

    public AggregatedSalesData() {
        totalAmount = 0.0;
        salesQty = 0;
        dataTimeStamp = new Date();
    }

    public AggregatedSalesData(final double saleAmount, final Date saleTimeStamp) {
        totalAmount = saleAmount;
        salesQty = 1;
        dataTimeStamp = saleTimeStamp;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public long getSalesQty() {
        return salesQty;
    }

    public Date getDataTimeStamp() {
        return dataTimeStamp;
    }

    public void updateTransactionData(final double saleAmount) {
        totalAmount += saleAmount;
        salesQty++;
    }

    public long getDataTimeStampInSecs() {
        return dataTimeStamp.getTime() / 1000;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
