package com.example.dosa_store.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static String baseURL = "https://54.179.41.66/api/v1/";
    private static Retrofit retrofit;

    public static Retrofit getClient(OkHttpClient client) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)  // Set the custom OkHttpClient here
                    .build();
        }
        return retrofit;
    }
}
