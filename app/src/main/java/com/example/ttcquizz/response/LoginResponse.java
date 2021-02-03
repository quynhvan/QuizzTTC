package com.example.ttcquizz.response;

import com.example.ttcquizz.model.DataUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse{
    @SerializedName("result")
    @Expose
    private DataUser result;

    public DataUser getData() {
        return result;
    }

    public void setData(DataUser result) {
        this.result = result;
    }

}

