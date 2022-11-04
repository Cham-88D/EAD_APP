package com.ead.fuelpass.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Station model
 */
public class Station {


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
    private String ownerId;

    public Station(String name, String location, String ownerId) {
        this.name = name;
        this.location = location;
        this.ownerId =  ownerId;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
