package com.example.maskapp.Data;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;


public class Data extends JSONObject {
    @SerializedName("features")
    private final ArrayList<Features> features;

    public Data(ArrayList<Features> features) {
        this.features = features;
    }

    public ArrayList<Features> getFeatures() {
        return features;
    }
}
