/**
 * TransactionDataStoreTest.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.util.AppUtils;

public class TransactionDataStoreTest {

    private TransactionDataStore dataStore;

    @Before
    public void init() {
        dataStore = TransactionDataStore.getInstance();
    }

    @Test
    public void testAdd_AddAtZeroSize() {
        dataStore.addSalesData(10000.0, new Date());
        assertEquals(1, dataStore.size());
    }

    @Test
    public void testAdd_AddTwoSalesInDifferentSecs() {
        final Calendar calendar = Calendar.getInstance();
        dataStore.addSalesData(10000.0, calendar.getTime());

        calendar.add(Calendar.SECOND, 1);
        dataStore.addSalesData(20000.0, calendar.getTime());

        assertEquals(2, dataStore.size());
    }

    @Test
    public void testAdd_AddTwoSalesInSameSecs() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        dataStore.addSalesData(10000.0, calendar.getTime());

        calendar.add(Calendar.MILLISECOND, 100);
        dataStore.addSalesData(20000.0, calendar.getTime());

        assertEquals(1, dataStore.size());
    }

    @Test
    public void testAdd_AddMoreThanDefaultSize() {
        final Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < AppUtils.DEFAULT_SIZE + 10; i++) {
            dataStore.addSalesData(10000.0, calendar.getTime());
            calendar.add(Calendar.SECOND, 1);
        }
        final long latestCalInSecs = calendar.getTimeInMillis() / 1000;
        final AggregatedSalesData headData = dataStore.peek();
        assertEquals(AppUtils.DEFAULT_SIZE, dataStore.size());
        assertEquals(60, latestCalInSecs - headData.getDataTimeStampInSecs());
    }

    @Test(expected = CloneNotSupportedException.class)
    public void testClone() throws CloneNotSupportedException {
        dataStore.clone();
    }

    @Test
    public void testGetSalesStatsInDataStore() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < AppUtils.DEFAULT_SIZE; i++) {
            dataStore.addSalesData(10.0, calendar.getTime());
            calendar.add(Calendar.SECOND, 1);
        }
        final StatsResponse response = dataStore.getSalesStatsInDataStore();
        assertEquals(600.0, response.getTotalSalesAmount(), 0.001);
    }

    @Test
    public void testGetSalesStatsInDataStore_NullDataStore() {
        assertNull(dataStore.getSalesStatsInDataStore());
    }

    @Test
    public void testGetSalesStatsInDataStore_NullElementsDataStore() {
        dataStore.addSalesData(0.0, null);
        final StatsResponse response = dataStore.getSalesStatsInDataStore();
        assertEquals(0.0, response.getTotalSalesAmount(), 0.001);
    }

    @After
    public void destroy() {
        dataStore.clear();
    }
}
