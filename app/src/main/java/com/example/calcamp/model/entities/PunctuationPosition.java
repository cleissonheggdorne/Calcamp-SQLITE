package com.example.calcamp.model.entities;

public class PunctuationPosition {
    PunctuationType punctuationType;
    Integer position;
    Integer score;

    public PunctuationPosition(){

    }
    public PunctuationPosition(PunctuationType punctuationType, Integer position, Integer score) {
        this.punctuationType = punctuationType;
        this.position = position;
        this.score = score;
    }

    public PunctuationType getPunctuationType() {
        return punctuationType;
    }

    public void setPunctuationType(PunctuationType punctuationType) {
        this.punctuationType = punctuationType;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
