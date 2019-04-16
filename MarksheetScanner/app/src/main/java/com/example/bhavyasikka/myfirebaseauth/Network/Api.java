package com.example.bhavyasikka.myfirebaseauth.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton pattern API consumer
 * GSON (JSON Serializer by Google) uses factory pattern
 */
public class Api {

    private static ApiService _api;

    private Api() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.167.3.138:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        _api = retrofit.create(ApiService.class);
    }

    public static ApiService Service() {
        if (_api == null) new Api();
        return _api;
    }
}
