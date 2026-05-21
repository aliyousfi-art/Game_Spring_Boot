package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HeartbeatController.class)
class HeartbeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeartbeatSensor heartbeatSensor;

    @Test
    void returnsCurrentBpm() throws Exception {
        given(heartbeatSensor.get()).willReturn(72);

        mockMvc.perform(get("/heartbeat"))
                .andExpect(status().isOk())
                .andExpect(content().string("72"));
    }

    @Test
    void statusIsLowBelow60() throws Exception {
        given(heartbeatSensor.get()).willReturn(55);

        mockMvc.perform(get("/heartbeat/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bpm").value(55))
                .andExpect(jsonPath("$.level").value("low"));
    }

    @Test
    void statusIsNormalBetween60And100() throws Exception {
        given(heartbeatSensor.get()).willReturn(80);

        mockMvc.perform(get("/heartbeat/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("normal"));
    }

    @Test
    void statusIsHighAbove100() throws Exception {
        given(heartbeatSensor.get()).willReturn(150);

        mockMvc.perform(get("/heartbeat/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level").value("high"));
    }
}
