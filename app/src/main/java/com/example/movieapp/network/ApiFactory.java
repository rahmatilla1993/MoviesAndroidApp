package com.example.movieapp.network;

import static com.example.movieapp.network.HttpConstants.*;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String BASE_URL = "https://api.kinopoisk.dev/";
    private static OkHttpClient okHttpClient;
    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            setInterceptor();
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    private static void setInterceptor() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header(ACCEPT, APPLICATION_JSON)
                            .header(API_KEY, API_TOKEN)
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }
}
