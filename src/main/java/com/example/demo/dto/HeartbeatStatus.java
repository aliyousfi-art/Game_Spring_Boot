package com.example.demo.dto;

public class HeartbeatStatus {

    private int value;
    private String status;
    
    public HeartbeatStatus() {
    }

    public HeartbeatStatus(int value, String status) {
        this.value = value;
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
