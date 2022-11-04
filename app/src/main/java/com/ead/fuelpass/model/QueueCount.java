package com.ead.fuelpass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Queue count model
 */
public class QueueCount {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("status")
    @Expose
    private String status;

    public QueueCount(String id, int count, String time, String status) {
        this.id = id;
        this.count = count;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
