package com.example.solidcourse.dataClasses.course.tasks;

import androidx.annotation.NonNull;

import com.example.solidcourse.dataClasses.course.AbstractTask;

import java.io.Serializable;

public class StudyTask extends AbstractTask implements Serializable {
    private static final long serialVersionUID = 8L;

    public StudyTask(String text, int score) {
        super(text, score);
    }

    @Override
    public void answer(String answer) {
        score = maxScore;
        state = ACCEPTED;
    }

    @NonNull
    @Override
    public String toString() {
        return "StudyTask{" +
                "state=" + state +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", maxScore=" + maxScore +
                '}';
    }
}
