package com.ead.fuelpass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//Queue data model with tank attribute
public class Queue2 {
    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("tank")
    @Expose
    private TankData tank;


    @SerializedName("customer")
    @Expose
    private String customer;

    @SerializedName("status")
    @Expose
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TankData getTank() {
        return tank;
    }

    public void setTank(TankData tank) {
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
