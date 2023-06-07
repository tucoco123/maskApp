package com.example.maskapp.Data;

import com.google.gson.annotations.SerializedName;

public class Features {

    @SerializedName("type")
    private final String type;

    @SerializedName("properties")
    private final DataProperties properties;



    @SerializedName("geometry")
    private final Geometry geometry;

    public Features(String type, DataProperties properties, Geometry geometry) {
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public DataProperties getProperties() {
        return properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
