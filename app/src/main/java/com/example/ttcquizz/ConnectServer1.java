
package com.example.ttcquizz;

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

public class ConnectServer1 {
    private static String API_BASE_URL = "http://192.168.20.110:8080/api/";
    private static Retrofit retrofit = null;
    private static String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjA4NjAwNzYwLCJleHAiOjE2MDkyMDU1NjB9.zcKoLVwEUPTecQ7nI3MXmCJkDe82SvBaZLgjGPeGzVcJCC1xfKPuNmIQbtkmxrnmjmV7zrJkFZ8y41p8J_0D6Q";
    public static MyService getApiService(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MyService.class);
    }
}
