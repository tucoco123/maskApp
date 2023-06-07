package com.example.maskapp.Data;

import com.google.gson.annotations.SerializedName;

public class DataProperties {
    @SerializedName("id")
    private final String id;
    @SerializedName("name")
    private final String name;
    @SerializedName("phone")
    private final String phone;
    @SerializedName("address")
    private final String address;
    @SerializedName("mask_adult")
    private final int numOfAdult;
    @SerializedName("mask_child")
    private final int numOfChild;
    @SerializedName("updated")
    private final String updated;
    @SerializedName("available")
    private final String available;
    @SerializedName("note")
    private final String note;
    @SerializedName("custom_note")
    private final String customNote;
    @SerializedName("website")
    private final String webSite;
    @SerializedName("county")
    private final String county;
    @SerializedName("town")
    private final String town;
    @SerializedName("cunli")
    private final String cunli;
    @SerializedName("service_periods")
    private final String service_periods;

    public DataProperties(String id, String name, String phone, String address, int numOfAdult, int numOfChild, String updated, String available, String note, String customNote, String webSite, String county, String town, String cunli, String service_periods) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.numOfAdult = numOfAdult;
        this.numOfChild = numOfChild;
        this.updated = updated;
        this.available = available;
        this.note = note;
        this.customNote = customNote;
        this.webSite = webSite;
        this.county = county;
        this.town = town;
        this.cunli = cunli;
        this.service_periods = service_periods;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getNumOfAdult() {
        return numOfAdult;
    }

    public int getNumOfChild() {
        return numOfChild;
    }

    public String getUpdated() {
        return updated;
    }

    public String getAvailable() {
        return available;
    }

    public String getNote() {
        return note;
    }

    public String getCustomNote() {
        return customNote;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }

    public String getCunli() {
        return cunli;
    }

    public String getService_periods() {
        return service_periods;
    }
}
