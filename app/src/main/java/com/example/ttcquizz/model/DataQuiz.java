package com.example.ttcquizz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataQuiz {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("testTime")
    @Expose
    private int testTime;
    @SerializedName("testDateBegin")
    @Expose
    private String testDateBegin;
    @SerializedName("testDateFinish")
    @Expose
    private String testDateFinish;
    @SerializedName("questionTestEntityList")
    @Expose
    private List<QuestionTestEntityList> questionTestEntityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(Integer testTime) {
        this.testTime = testTime;
    }

    public String getTestDateBegin() {
        return testDateBegin;
    }

    public void setTestDateBegin(String testDateBegin) {
        this.testDateBegin = testDateBegin;
    }

    public String getTestDateFinish() {
        return testDateFinish;
    }

    public void setTestDateFinish(String testDateFinish) {
        this.testDateFinish = testDateFinish;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public List<QuestionTestEntityList> getQuestionTestEntityList() {
        return questionTestEntityList;
    }

    public void setQuestionTestEntityList(List<QuestionTestEntityList> questionTestEntityList) {
        this.questionTestEntityList = questionTestEntityList;
    }
}
