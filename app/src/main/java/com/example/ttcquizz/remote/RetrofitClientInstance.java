package com.example.ttcquizz.remote;

import android.content.Context;
import android.util.Log;

import com.example.ttcquizz.service.MyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static String API_BASE_URL = "http://192.168.20.110:8080/api/";
    private static Retrofit retrofit;
    private static Gson gson;
    private static Context context;

    public static Retrofit getRetrofitInstance(Context context) {
        String token = SessionManager.getInstance(context).getDeviceToken();
        Log.e("kevin", "token: "  + token );
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();
        if(retrofit == null) {
            gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(API_BASE_URL)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static MyService getMyService(Context context) {
        MyService myService = getRetrofitInstance(context).create(MyService.class);
        return myService;
    }

}
