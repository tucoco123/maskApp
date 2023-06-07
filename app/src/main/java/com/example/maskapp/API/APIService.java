package com.example.maskapp.API;

import com.example.maskapp.Data.Data;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("points.json")
    Call<Data> getData();
}
