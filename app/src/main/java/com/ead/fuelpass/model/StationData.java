package com.ead.fuelpass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Station data with owner attribute
 */
public class StationData {
    @SerializedName("shedId")
    @Expose
    private String shedId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("shedMaster")
    @Expose
    private Owner shedMaster;

    public StationData(String shedId, String name, String location, Owner shedMaster) {
        this.shedId = shedId;
        this.name = name;
        this.location = location;
        this.shedMaster = shedMaster;
    }

    public String getShedId() {
        return shedId;
    }

    public void setShedId(String shedId) {
        this.shedId = shedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Owner getShedMaster() {
        return shedMaster;
    }

    public void setShedMaster(Owner shedMaster) {
        this.shedMaster = shedMaster;
    }
}
