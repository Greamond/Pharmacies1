package com.example.pharmacies.ApiClasses;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static IApi getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.208.9:5247/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(IApi.class);
    }
}
