package com.example.ttcquizz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

   private String mess;
   private boolean isRead;

    public Message(String mess, boolean isRead) {
        this.mess = mess;
        this.isRead = isRead;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}