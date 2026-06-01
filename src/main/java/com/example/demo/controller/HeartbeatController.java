package com.example.demo.controller;

import com.example.demo.sensor.HeartbeatSensor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {

    private final HeartbeatSensor heartbeatSensor;

    // Injection par constructeur
    public HeartbeatController(HeartbeatSensor heartbeatSensor) {
        this.heartbeatSensor = heartbeatSensor;
    }

    @GetMapping("/heartbeat")
    public int getHeartbeat() {
        return heartbeatSensor.get();
    }
}
