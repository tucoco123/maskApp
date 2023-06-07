package com.example.maskapp.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Geometry {
    @SerializedName("type")
    private final String type;


    @SerializedName("coordinates")
    private final ArrayList<String> coordinates;


    public Geometry(String type, ArrayList<String> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }


    public String getType() {
        return type;
    }

    public ArrayList<String> getCoordinates() {
        return coordinates;
    }
}
