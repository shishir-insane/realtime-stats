package com.sk.sales.stats.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sk.sales.stats.dto.StatsResponse;
import com.sk.sales.stats.util.AppUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(StatsController.class)
public class StatsControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private StatsController controller;

    private StatsResponse response;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        response = new StatsResponse(1000000.00, 45.04);
    }

    @Test
    public void testGetRealTimeStats() throws Exception {
        mvc.perform(get(AppUtils.ENDPOINT_STATS).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }

}
