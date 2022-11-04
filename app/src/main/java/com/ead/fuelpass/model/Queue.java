package com.ead.fuelpass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Queue model
 */
public class Queue {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("tank")
    @Expose
    private String tank;

    @SerializedName("customer")
    @Expose
    private String customer;

    @SerializedName("status")
    @Expose
    private String status;

    public Queue(String id, String tank, String customer, String status) {
        this.id = id;
        this.tank = tank;
        this.customer = customer;
        this.status = status;
    }

    public Queue(String tank, String customer, String status) {
        this.tank = tank;
        this.customer = customer;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTank() {
        return tank;
    }

    public void setTank(String tank) {
        this.tank = tank;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
