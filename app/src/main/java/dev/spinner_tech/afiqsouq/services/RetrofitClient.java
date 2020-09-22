package dev.spinner_tech.afiqsouq.services;

import android.content.Context;

import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.api.api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class RetrofitClient {
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private Context context;


    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }


    public api getApi() {
        return retrofit.create(api.class);
    }
}