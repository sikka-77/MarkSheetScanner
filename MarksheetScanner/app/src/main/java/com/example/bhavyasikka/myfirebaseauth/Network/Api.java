package com.example.bhavyasikka.myfirebaseauth.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton pattern API consumer
 * GSON (JSON Serializer by Google) uses factory pattern
 */
public class Api {

    private static ApiService _api;

    private Api() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.167.5.178:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        _api = retrofit.create(ApiService.class);
    }

    public static ApiService Service() {
        if (_api == null) new Api();
        return _api;
    }
}
