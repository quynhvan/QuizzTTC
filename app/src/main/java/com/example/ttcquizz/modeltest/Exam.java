package com.example.ttcquizz.modeltest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exam {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("testName")
    @Expose
    private String testName;
    @SerializedName("testTime")
    @Expose
    private Integer testTime;
    @SerializedName("testDateBegin")
    @Expose
    private String testDateBegin;
    @SerializedName("testDateFinish")
    @Expose
    private String testDateFinish;
    @SerializedName("questionTestEntityList")
    @Expose
    private List<QuestionTestEntityList> questionTestEntityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getTestTime() {
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

    public List<QuestionTestEntityList> getQuestionTestEntityList() {
        return questionTestEntityList;
    }

    public void setQuestionTestEntityList(List<QuestionTestEntityList> questionTestEntityList) {
        this.questionTestEntityList = questionTestEntityList;
    }

}