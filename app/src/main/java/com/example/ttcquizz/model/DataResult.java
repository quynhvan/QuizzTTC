package com.example.ttcquizz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataResult {
    @SerializedName("scoreMultipleChoice")
    @Expose
    private String scoreMultipleChoice;

    @SerializedName("scoreEssay")
    @Expose
    private String scoreEssay;

    public DataResult() {
    }

    public DataResult(String scoreMultipleChoice, String scoreEssay) {
        this.scoreMultipleChoice = scoreMultipleChoice;
        this.scoreEssay = scoreEssay;
    }

    public String getScoreMultipleChoice() {
        return scoreMultipleChoice;
    }

    public void setScoreMultipleChoice(String scoreMultipleChoice) {
        this.scoreMultipleChoice = scoreMultipleChoice;
    }

    public String getScoreEssay() {
        return scoreEssay;
    }

    public void setScoreEssay(String scoreEssay) {
        this.scoreEssay = scoreEssay;
    }
}
