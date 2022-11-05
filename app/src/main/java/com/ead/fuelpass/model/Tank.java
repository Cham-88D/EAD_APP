package com.ead.fuelpass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Fuel Tank model
 */
public class Tank {

    @SerializedName("tankId")
    @Expose
    private String tankId;

    @SerializedName("fuelType")
    @Expose
    private String fuelType;

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("shed")
    @Expose
    private String shed;

    public Tank(String fuelType, boolean status, String shed) {
        this.fuelType = fuelType;
        this.status = status;
        this.shed = shed;
    }

    public Tank(String tankId, String fuelType, boolean status, String shed) {
        this.tankId = tankId;
        this.fuelType = fuelType;
        this.status = status;
        this.shed = shed;
    }

    public String getTankId() {
        return tankId;
    }

    public void setTankId(String tankId) {
        this.tankId = tankId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getShed() {
        return shed;
    }

    public void setShed(String shed) {
        this.shed = shed;
    }
}
