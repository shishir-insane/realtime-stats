package com.sk.sales.stats.data;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.sk.sales.stats.util.AppUtils;

public class TransactionDataStoreTest {

    private TransactionDataStore dataStore;

    @Before
    public void init() {
        dataStore = new TransactionDataStore();
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
        for (int i = 0; i < AppUtils.DEFAULT_SIZE + 1; i++) {
            dataStore.addSalesData(10000.0, calendar.getTime());
            calendar.add(Calendar.SECOND, 1);
        }
        assertEquals(AppUtils.DEFAULT_SIZE, dataStore.size());
    }

    @Test(expected = CloneNotSupportedException.class)
    public void testClone() throws CloneNotSupportedException {
        dataStore.clone();
    }

}