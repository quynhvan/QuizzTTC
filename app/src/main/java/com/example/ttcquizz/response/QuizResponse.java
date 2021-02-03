package com.example.ttcquizz.response;

import com.example.ttcquizz.model.DataQuiz;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizResponse extends BaseResponse{
    @SerializedName("result")
    @Expose
    private List<DataQuiz> result;

    public List<DataQuiz> getData() {
        return result;
    }

    public void setData(List<DataQuiz> result) {
        this.result = result;
    }
}
