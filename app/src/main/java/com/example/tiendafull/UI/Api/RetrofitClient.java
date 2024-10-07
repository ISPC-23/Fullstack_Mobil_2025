package com.example.tiendafull.UI.Api;


import com.example.tiendafull.UI.Models.SessionManager;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://deploy-django-sjie.onrender.com/api/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(SessionManager sessionManager) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = OkHttpProvider.getClient(sessionManager);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return retrofit;
    }
}