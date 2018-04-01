package com.sk.sales.stats.data;

import java.util.Date;

public class SalesData {

    private final double saleAmount;
    private final Date saleTimeStamp;

    public SalesData(final double saleAmount, final Date saleTimeStamp) {
        this.saleAmount = saleAmount;
        this.saleTimeStamp = saleTimeStamp;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public Date getSaleTimeStamp() {
        return saleTimeStamp;
    }

    public long getSaleTimeStampInSecs() {
        return saleTimeStamp.getTime() / 1000;
    }

}
