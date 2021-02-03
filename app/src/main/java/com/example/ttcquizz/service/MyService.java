package com.example.ttcquizz.service;

import com.example.ttcquizz.modeltest.Answer;
import com.example.ttcquizz.modeltest.Example;
import com.example.ttcquizz.request.ChangePasswordRequest;
import com.example.ttcquizz.request.LoginRequest;
import com.example.ttcquizz.response.BaseResponse;
import com.example.ttcquizz.response.LoginResponse;
import com.example.ttcquizz.response.QuizResponse;
import com.example.ttcquizz.response.ResultResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyService {
    @POST("auth/login")
    Call<LoginResponse> checkLogin(@Body LoginRequest loginRequest);

    @PUT("auth/password")
    Call<BaseResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("lists")
    Call<QuizResponse> getQuiz();

    @GET("lists/{id}")
    Call<ResultResponse> getResult(@Path(value = "id", encoded = true) int id);

    @GET("lists")
    Call<Example> getListExam();

    @POST("tasks/{id}")
    Call<LoginResponse> sendAnswer(@Path(value = "id", encoded = true) int id, @Body Answer answer);
}
