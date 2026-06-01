package com.example.demo;

import com.example.demo.sensor.RandomHeartbeat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomHeartbeatTest {

    @Test
    public void testRandomHeartbeatRange() {
        RandomHeartbeat sensor = new RandomHeartbeat();

        for (int i = 0; i < 100; i++) {
            int value = sensor.get();
            assertTrue(value >= 40 && value <= 230, 
                    "Value " + value + " is not in range [40, 230]");
        }
    }

    @Test
    public void testRandomHeartbeatVariability() {
        RandomHeartbeat sensor = new RandomHeartbeat();
        
        int first = sensor.get();
        int second = sensor.get();
        
        // Au moins une des deux valeurs doit être différente (à 99.999% de probabilité)
        assertNotEquals(first, second, "Random values should vary");
    }
}
