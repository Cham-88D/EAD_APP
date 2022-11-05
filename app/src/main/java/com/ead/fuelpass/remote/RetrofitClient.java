package com.ead.fuelpass.remote;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.ead.fuelpass.cons.Constants;


/**
 * Retrofit Client
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constants.URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient())
                    .build();
        }
        return retrofit;
    }
}