/**
 * StatsControllerTest.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.controller;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sk.sales.stats.data.TransactionDataStore;
import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.service.StatsService;
import com.sk.sales.stats.util.AppUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(StatsController.class)
public class StatsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatsService statsService;

    @InjectMocks
    private StatsController controller;

    private StatsResponse response;
    private TransactionDataStore dataStore;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        dataStore = TransactionDataStore.getInstance();
        response = new StatsResponse(1000000.00, 45.04);
        final Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < AppUtils.DEFAULT_SIZE + 1; i++) {
            dataStore.addSalesData(10.0, calendar.getTime());
            calendar.add(Calendar.SECOND, 1);
        }
    }

    @Test
    public void testUpdateSales() throws Exception {
        doNothing().when(statsService).updateSalesData(anyDouble(), isA(Date.class));
        mvc.perform(post(AppUtils.ENDPOINT_SALES).param(AppUtils.REQ_PARAM_SALES_AMOUNT, "10.0")
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testUpdateSales_WithNonDoubleNumberString() throws Exception {
        doNothing().when(statsService).updateSalesData(anyDouble(), isA(Date.class));
        mvc.perform(post(AppUtils.ENDPOINT_SALES).param(AppUtils.REQ_PARAM_SALES_AMOUNT, "10")
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testUpdateSales_WithNonNumberString() throws Exception {
        doNothing().when(statsService).updateSalesData(anyDouble(), isA(Date.class));
        mvc.perform(post(AppUtils.ENDPOINT_SALES).param(AppUtils.REQ_PARAM_SALES_AMOUNT, "test")
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetRealTimeStats() throws Exception {
        when(statsService.getRealTimeStats()).thenReturn(response);
        mvc.perform(get(AppUtils.ENDPOINT_STATS).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }

    @After
    public void destroy() {
        dataStore.clear();
    }
}
