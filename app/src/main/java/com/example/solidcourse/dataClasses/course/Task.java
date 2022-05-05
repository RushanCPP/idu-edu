package com.example.solidcourse.dataClasses.course;

public interface Task {
    int NOT_VERIFIED = 0;
    int ACCEPTED = 1;
    int NOT_BEGIN = 2;
    int NOT_ACCEPTED = 3;

    String getText();

    boolean isAccepted();

    int getScore();

    int getMaxScore();

    void setMaxScore(int score);

    void setScore(int score);

    void setText(String text);

    int getState();

    void restart();

    void setState(int state);

    void answer(String answer);
}