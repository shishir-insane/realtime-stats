/**
 * AggregatedSalesData.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AggregatedSalesData implements Serializable {

    private static final long serialVersionUID = 3228872143499979889L;

    private double totalAmount;
    private long salesQty;
    private final Date dataTimeStamp;

    /**
     * Instantiates a new aggregated sales data.
     */
    public AggregatedSalesData() {
        totalAmount = 0.0;
        salesQty = 0;
        dataTimeStamp = new Date();
    }

    /**
     * Instantiates a new aggregated sales data.
     *
     * @param saleAmount
     *            the sale amount
     * @param saleTimeStamp
     *            the sale time stamp
     */
    public AggregatedSalesData(final double saleAmount, final Date saleTimeStamp) {
        totalAmount = saleAmount;
        salesQty = 1;
        dataTimeStamp = saleTimeStamp;
    }

    /**
     * Gets the total amount.
     *
     * @return the total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Gets the sales qty.
     *
     * @return the sales qty
     */
    public long getSalesQty() {
        return salesQty;
    }

    /**
     * Gets the data time stamp.
     *
     * @return the data time stamp
     */
    public Date getDataTimeStamp() {
        return dataTimeStamp;
    }

    /**
     * Update transaction data.
     *
     * @param saleAmount
     *            the sale amount
     */
    public void updateTransactionData(final double saleAmount) {
        totalAmount += saleAmount;
        salesQty++;
    }

    /**
     * Gets the data time stamp in secs.
     *
     * @return the data time stamp in secs
     */
    public long getDataTimeStampInSecs() {
        return dataTimeStamp.getTime() / 1000;
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
