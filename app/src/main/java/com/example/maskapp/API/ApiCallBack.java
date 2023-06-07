package com.example.maskapp.API;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallBack<Data> implements Callback {
    public abstract void onSuccess(Data response);

    public abstract void onError(@NonNull ErrorResponse errorResponse);
    @Override
    public void onResponse(@NonNull Call call, Response response) {
        Data data = (Data) response.body();
        if(response.isSuccessful()){
            onSuccess(data);
        }else{
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Api Failed !");
            onError(errorResponse);
        }
    }

    @Override
    public void onFailure(@NonNull Call call, Throwable t) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(t.getMessage());
        onError(errorResponse);
    }
}
