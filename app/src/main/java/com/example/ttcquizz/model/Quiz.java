package com.example.ttcquizz.model;


public class Quiz {
    private int id;
    private String testName;
    private int testTime;
    private String testDateBegin;
    private String testDateFinish;
    private boolean status;
    private DataResult result;

    public Quiz() {
    }

    public Quiz(int id, String testName, DataResult result) {
        this.id = id;
        this.testName = testName;
        this.result = result;
    }

    public Quiz(int id, String testName, String testDateFinish) {
        this.id = id;
        this.testName = testName;
        this.testDateFinish = testDateFinish;
    }

    public Quiz(int id, String testName, int testTime, String testDateBegin, String testDateFinish, boolean status) {
        this.id = id;
        this.testName = testName;
        this.testTime = testTime;
        this.testDateBegin = testDateBegin;
        this.testDateFinish = testDateFinish;
        this.status = status;
    }

    public Quiz(int id, String testName, int testTime, String testDateBegin, String testDateFinish, boolean status, DataResult result) {
        this.id = id;
        this.testName = testName;
        this.testTime = testTime;
        this.testDateBegin = testDateBegin;
        this.testDateFinish = testDateFinish;
        this.status = status;
        this.result = result;
    }

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

    public void setTestTime(int testTime) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataResult getResult() {
        return result;
    }

    public void setResult(DataResult result) {
        this.result = result;
    }
}
