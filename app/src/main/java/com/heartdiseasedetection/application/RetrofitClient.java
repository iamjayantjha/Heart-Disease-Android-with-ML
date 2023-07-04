package com.heartdiseasedetection.application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://shark-app-7i3wz.ondigitalocean.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }return retrofit;
    }
}