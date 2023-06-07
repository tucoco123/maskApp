package com.example.maskapp.API;

import com.example.maskapp.Data.Data;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public Retrofit retrofit;
    private final APIService service;

    public APIClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/kiang/pharmacies/master/json/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        service = retrofit.create(APIService.class);
    }
    public Call<Data> getData(){
        return service.getData();
    }
}
