package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomHeartbeat implements HeartbeatSensor {

    private static final int MIN_BPM = 55;
    private static final int MAX_BPM = 180;

    private final Random random = new Random();

    @Override
    public int get() {
        return MIN_BPM + random.nextInt(MAX_BPM - MIN_BPM + 1);
    }
}
