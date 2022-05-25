package com.example.solidcourse.dataClasses.course;

import androidx.annotation.NonNull;

import java.io.Serializable;

public abstract class AbstractTask implements Task, Serializable {
    private static final long serialVersionUID = 1L;

    protected int state = NOT_BEGIN;
    protected String text;
    protected int score = 0;
    protected int maxScore;
    protected long id;
    protected long lessonId;

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    abstract public void answer(String answer);

    public AbstractTask(String text, int score) {
        this.text = text;
        this.maxScore = score;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isAccepted() {
        return score == maxScore && state == ACCEPTED;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int getMaxScore() {
        return maxScore;
    }

    @Override
    public void setMaxScore(int score) {
        maxScore = score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void restart() {
        state = NOT_BEGIN;
        score = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "AbstractTask{" +
                "state=" + state +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", maxScore=" + maxScore +
                '}';
    }
}
