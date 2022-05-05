package com.example.solidcourse.dataClasses.course;

import androidx.annotation.NonNull;

public abstract class TaskDecorator implements Task {
    protected Task decoratedTask;

    public TaskDecorator(Task decoratedTask) {
        this.decoratedTask = decoratedTask;
    }

    @Override
    public String getText() {
        return decoratedTask.getText();
    }

    @Override
    public boolean isAccepted() {
        return decoratedTask.isAccepted();
    }

    @Override
    public int getScore() {
        return decoratedTask.getScore();
    }

    @Override
    public int getMaxScore() {
        return decoratedTask.getMaxScore();
    }

    @Override
    public void setScore(int score) {
        decoratedTask.setScore(score);
    }

    @Override
    public void setText(String text) {
        decoratedTask.setText(text);
    }

    @Override
    public void restart() {
        decoratedTask.restart();
    }

    @Override
    public int getState() {
        return decoratedTask.getState();
    }

    public void setState(int state) {
        decoratedTask.setState(state);
    }

    @Override
    public void setMaxScore(int score) {
        decoratedTask.setMaxScore(score);
    }

    @Override
    abstract public void answer(String answer);

    public void setDecoratedTask(Task decoratedTask) {
        this.decoratedTask = decoratedTask;
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskDecorator{" +
                "decoratedTask=" + decoratedTask +
                '}';
    }
}
