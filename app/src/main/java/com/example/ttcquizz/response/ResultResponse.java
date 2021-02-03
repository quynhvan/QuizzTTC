package com.example.ttcquizz.response;

import com.example.ttcquizz.model.DataResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultResponse extends BaseResponse{
    @SerializedName("result")
    @Expose
    private DataResult result;

    public DataResult getData() {
        return result;
    }

    public void setData(DataResult result) {
        this.result = result;
    }
}
