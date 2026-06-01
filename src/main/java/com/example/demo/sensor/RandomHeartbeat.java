package com.example.demo.sensor;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomHeartbeat implements HeartbeatSensor {

    private final Random random = new Random();

    @Override
    public int get() {
        return random.nextInt(191) + 40;
    }
}