package com.example.ttcquizz.modeltest;

public class Answer {
    private int id;
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Answer(int id, String answer) {
        this.id = id;
        this.answer = answer;
    }
}
