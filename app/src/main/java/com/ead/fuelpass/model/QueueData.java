package com.ead.fuelpass.model;

public class QueueData {

    private String q_id;
    private String tank_id;
    private String staion_id;
    private String status;
    private String queue_status;
    private String type;
    private String time;
    private int count;

    public QueueData(String q_id, String tank_id, String staion_id, String status, String queue_status, String type, String time, int count) {
        this.q_id = q_id;
        this.tank_id = tank_id;
        this.staion_id = staion_id;
        this.status = status;
        this.queue_status = queue_status;
        this.type = type;
        this.time = time;
        this.count = count;
    }

    public String getQ_id() {
        return q_id;
    }

    public void setQ_id(String q_id) {
        this.q_id = q_id;
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

    public String getQueue_status() {
        return queue_status;
    }

    public void setQueue_status(String queue_status) {
        this.queue_status = queue_status;
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
