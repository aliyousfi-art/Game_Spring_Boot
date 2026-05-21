package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomHeartbeatTest {

    @Test
    void bpmStaysInRealisticRange() {
        RandomHeartbeat sensor = new RandomHeartbeat();

        for (int i = 0; i < 1000; i++) {
            int bpm = sensor.get();
            assertThat(bpm).isBetween(55, 180);
        }
    }
}
