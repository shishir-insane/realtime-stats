/**
 * TransactionDataStore.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.data;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

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
public class TransactionDataStore extends ConcurrentLinkedQueue<AggregatedSalesData> {

    private static final long serialVersionUID = 2435457440516806502L;

    private static volatile TransactionDataStore transactionDataStore;

    private AggregatedSalesData tailData;

    /**
     * Instantiates a new transaction data store.
     */
    private TransactionDataStore() {
        super();
        // a raw prevention from instantiation from Reflection API
        if (transactionDataStore != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        // if tailData is null, the size of the data store is 0
        tailData = null;
    }

    /**
     * Gets the single instance of TransactionDataStore.
     *
     * @return single instance of TransactionDataStore
     */
    public static TransactionDataStore getInstance() {
        if (transactionDataStore == null) {
            synchronized (TransactionDataStore.class) {
                if (transactionDataStore == null) {
                    transactionDataStore = new TransactionDataStore();
                }
            }
        }
        return transactionDataStore;
    }

    /**
     * Read resolve to make TransactionDataStore singleton from serialize and
     * deserialize operations.
     *
     * @return the transaction data store
     */
    protected TransactionDataStore readResolve() {
        return getInstance();
    }

    /**
     * This method to add the sales data to the data store while bounding the
     * max size of the data collection. As the queue size is bounded after
     * insertion, this method will never throw IllegalStateException or return
     * false.
     *
     * @param salesAmount
     *            the sales amount
     * @param saleTimestamp
     *            the sale timestamp
     * @return true if element is added or not
     */
    public boolean addSalesData(final double salesAmount, final Date saleTimestamp) {
        final AggregatedSalesData aggregatedSalesData = new AggregatedSalesData(salesAmount, saleTimestamp);
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
     * Use addSalesData(double, Date) to insert new data to data store.
     *
     * @param aggregatedSalesData
     *            the aggregated sales data
     * @return true, if successful
     */
    @Override
    public boolean add(final AggregatedSalesData aggregatedSalesData) {
        throw new RuntimeException("Use addSalesData(double, Date) to insert new data to data store.");
    }

    /**
     * Gets the sales stats in data store.
     *
     * @return the sales stats in data store
     */
    public StatsResponse getSalesStatsInDataStore() {
        StatsResponse response = null;
        double totalSalesAmount = 0.0;
        double totalSalesQty = 0.0;
        if (!isEmpty()) {
            // An array containing all of the elements in this queue, in proper
            // sequence; the runtime type of the returned array is that of the
            // specified array.
            final AggregatedSalesData[] dataArray = toArray(new AggregatedSalesData[AppUtils.DEFAULT_SIZE]);
            for (final AggregatedSalesData data : dataArray) {
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
        if (isEmpty()) {
            // check if the data store is empty
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
