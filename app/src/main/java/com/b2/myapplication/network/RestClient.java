package com.b2.myapplication.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;


import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rizwan Ahmed on 08/10/2020.
 */
public class RestClient {
    Retrofit builder;

    public RestClient() {

        String API_BASE_URL = "https://www.fakestoreapi.com/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);


        httpClient.addInterceptor(new Interceptor() {
            @Override
            @NonNull
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();

                requestBuilder.addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();
                Response response = null;
                try {
                    response = chain.proceed(request);
                } catch (Error | NullPointerException e2) {
                    Log.v("ROME parse error2 ", "unknown: 2" + e2.toString());
                }
                return response;
            }
        });

        OkHttpClient shortHttpClient = httpClient.readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
        builder = new Retrofit.Builder().
                baseUrl(API_BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                client(shortHttpClient).
                build();
    }


    public <S> S createService(Class<S> serviceClass) {
        return builder.create(serviceClass);
    }
}