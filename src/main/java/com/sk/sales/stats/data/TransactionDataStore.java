/**
 * TransactionDataStore.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.data;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

import com.sk.sales.stats.aop.TimedLog;
import com.sk.sales.stats.dto.StatsResponse;
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
        // if tailData is null, the size of the data store is 0
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
    @TimedLog
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

    /**
     * Gets the sales stats in data store.
     *
     * @return the sales stats in data store
     */
    @TimedLog
    public StatsResponse getSalesStatsInDataStore() {
        StatsResponse response = null;
        double totalSalesAmount = 0.0;
        double totalSalesQty = 0.0;
        if (null != tailData) {
            // The elements by below iterator will be returned in order from
            // first (head) to last (tail).
            final Iterator<AggregatedSalesData> itr = iterator();
            while (itr.hasNext()) {
                final AggregatedSalesData data = itr.next();
                if (null != data) {
                    totalSalesAmount += data.getTotalAmount();
                    totalSalesQty += data.getSalesQty();
                }
            }
            if (totalSalesQty > 0) {
                response = new StatsResponse(totalSalesAmount, totalSalesAmount / totalSalesQty);
            }
        }
        return response;
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
     * @return true, if either the size of the queue is zero or the aggregated
     *         sales data for the given second from the timestamp is already
     *         present at the tail of the queue
     */
    private boolean shouldInsertAtTail(final Date saleTimestamp) {
        boolean shouldInsert = false;
        if (null == tailData) {
            // check if the data store size is 0
            shouldInsert = true;
        } else if (null != saleTimestamp) {
            // check if the aggregation for the requested timestamp second is
            // present in data store
            final long saleTimeStampInSecs = saleTimestamp.getTime() / 1000;
            if (saleTimeStampInSecs - tailData.getDataTimeStampInSecs() > 0) {
                shouldInsert = true;
            }
        }
        return shouldInsert;
    }
}
