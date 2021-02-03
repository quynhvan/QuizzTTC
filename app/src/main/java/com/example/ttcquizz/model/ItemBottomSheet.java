package com.example.ttcquizz.model;

public class ItemBottomSheet {
    private int id;
    private boolean isAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public ItemBottomSheet(int id, boolean isAnswer) {
        this.id = id;
        this.isAnswer = isAnswer;
    }
}
