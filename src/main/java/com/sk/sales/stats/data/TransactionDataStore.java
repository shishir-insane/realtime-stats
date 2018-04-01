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

    public TransactionDataStore() {
        super();
        tailData = null;
    }

    public boolean addSalesData(final double salesAmount, final Date saleTimestamp) {
        return add(new AggregatedSalesData(salesAmount, saleTimestamp));

    }

    /**
     * This method to add the data to the data store while maintaining the max
     * size of the data collection
     *
     * @param data
     * @return
     */
    @Override
    public boolean add(final AggregatedSalesData data) {
        boolean isInserted = false;
        if (shouldInsertAtTail(data.getDataTimeStamp())) {
            isInserted = super.add(data);
            tailData = data;
        } else {
            tailData.updateTransactionData(data.getTotalAmount());
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
     *
     * @param data
     * @return
     */
    private boolean shouldInsertAtTail(final Date saleTimestamp) {
        boolean shouldInsert = false;
        final long saleTimeStampInSecs = saleTimestamp.getTime() / 1000;
        if (size() == 0 || saleTimeStampInSecs - tailData.getDataTimeStampInSecs() > 0) {
            shouldInsert = true;
        }
        return shouldInsert;
    }
}
