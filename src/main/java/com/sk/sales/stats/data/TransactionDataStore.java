/**
 * TransactionDataStore.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.data;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

import com.sk.sales.stats.util.AppUtils;

/**
 * This class serves as the data store for the sales transaction data. It stores
 * data only for the defined period which is actually the refresh interval of
 * the dashboard and gets rid of the data which is no longer required by the
 * dashboard. It uses an bounded thread-safe queue and derives properties from
 * ConcurrentLinkedQueue which employs Simple, Fast, and Practical Non-Blocking
 * and Blocking Concurrent Queue Algorithms by Maged M. Michael and Michael L.
 * Scott.
 *
 * The type of elements of this class is defined in AggregatedSalesData.
 */
@Component
public class TransactionDataStore extends ConcurrentLinkedQueue<AggregatedSalesData> {

    private static final long serialVersionUID = 2435457440516806502L;

    private AggregatedSalesData tailData;

    /**
     * Instantiates a new transaction data store.
     */
    public TransactionDataStore() {
        super();
        tailData = null;
    }

    /**
     * Adds the sales data.
     *
     * @param salesAmount
     *            the sales amount
     * @param saleTimestamp
     *            the sale timestamp
     */
    public void addSalesData(final double salesAmount, final Date saleTimestamp) {
        add(new AggregatedSalesData(salesAmount, saleTimestamp));
    }

    /**
     * This method to add the data to the data store while bounding the max size
     * of the data collection. As the queue size is bounded after insertion,
     * this method will never throw IllegalStateException or return false.
     *
     * @param aggregatedSalesData
     *            the aggregated sales data
     * @return true, if successful
     */
    @Override
    public boolean add(final AggregatedSalesData aggregatedSalesData) {
        boolean isInserted = false;
        if (shouldInsertAtTail(aggregatedSalesData.getDataTimeStamp())) {
            isInserted = offer(aggregatedSalesData);
            tailData = aggregatedSalesData;
        } else {
            tailData.updateTransactionData(aggregatedSalesData.getTotalAmount());
        }
        if (size() > AppUtils.DEFAULT_SIZE) {
            remove();
        }
        return isInserted;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Should insert at tail.
     *
     * @param saleTimestamp
     *            the sale timestamp
     * @return true, if successful
     */
    private boolean shouldInsertAtTail(final Date saleTimestamp) {
        boolean shouldInsert = false;
        final long saleTimeStampInSecs = saleTimestamp.getTime() / 1000;
        if (null == tailData || saleTimeStampInSecs - tailData.getDataTimeStampInSecs() > 0) {
            shouldInsert = true;
        }
        return shouldInsert;
    }
}
