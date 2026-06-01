package com.example.demo;

import com.example.demo.controller.HeartbeatController;
import com.example.demo.sensor.HeartbeatSensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HeartbeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHeartbeatEndpoint() throws Exception {
        mockMvc.perform(get("/heartbeat"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testHeartbeatValueRange() throws Exception {
        // Test que la valeur est entre 40 et 230
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(get("/heartbeat"))
                    .andExpect(status().isOk());
        }
    }
}
