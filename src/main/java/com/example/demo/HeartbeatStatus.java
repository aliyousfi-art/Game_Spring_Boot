package com.example.demo;

public record HeartbeatStatus(int bpm, String level) {

    public static HeartbeatStatus from(int bpm) {
        String level;
        if (bpm < 60) {
            level = "low";
        } else if (bpm <= 100) {
            level = "normal";
        } else {
            level = "high";
        }
        return new HeartbeatStatus(bpm, level);
    }
}
