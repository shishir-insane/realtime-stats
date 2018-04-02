package com.sk.sales.stats.service;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sk.sales.stats.data.TransactionDataStore;
import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.service.impl.StatsServiceImpl;
import com.sk.sales.stats.util.AppUtils;

public class StatsServiceImplTest {

    private TransactionDataStore dataStore;
    private StatsServiceImpl statsService;

    @Before
    public void init() {
        statsService = new StatsServiceImpl();
        dataStore = TransactionDataStore.getInstance();
        final Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < AppUtils.DEFAULT_SIZE + 1; i++) {
            dataStore.addSalesData(10.0, calendar.getTime());
            calendar.add(Calendar.SECOND, 1);
        }
    }

    @Test
    public void testUpdateSalesData() {
        statsService.updateSalesData(10.0, new Date());
    }

    @Test
    public void testGetRealTimeStats() {
        final StatsResponse response = statsService.getRealTimeStats();
        assertNotNull(response);
    }

    @After
    public void destroy() {
        dataStore.clear();
        dataStore.setTailData(null);
    }
}
