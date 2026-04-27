package com.neusoft.yth.nepBackend.nepv;

import com.neusoft.yth.nepBackend.nepv.dto.ProvinceStatsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProvinceSO2Stats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nepv/statistics/province/so2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void getDashboardData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nepv/statistics/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
