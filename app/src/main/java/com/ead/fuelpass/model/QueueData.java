package com.ead.fuelpass.model;

public class QueueData {

    private String tank_id;
    private String staion_id;
    private String status;
    private String type;
    private String time;
    private int count;

    public QueueData(String tank_id, String staion_id, String status, String type, String time, int count) {
        this.tank_id = tank_id;
        this.staion_id = staion_id;
        this.status = status;
        this.type = type;
        this.time = time;
        this.count = count;
    }

    public String getTank_id() {
        return tank_id;
    }

    public void setTank_id(String tank_id) {
        this.tank_id = tank_id;
    }

    public String getStaion_id() {
        return staion_id;
    }

    public void setStaion_id(String staion_id) {
        this.staion_id = staion_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
