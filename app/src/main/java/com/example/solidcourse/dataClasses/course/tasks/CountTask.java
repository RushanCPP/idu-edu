package com.example.solidcourse.dataClasses.course.tasks;

import androidx.annotation.NonNull;

import com.example.solidcourse.dataClasses.course.AbstractTask;

import java.io.Serializable;

public class CountTask extends AbstractTask implements Serializable {
    private static final long serialVersionUID = 7L;

    String answerValue;
    public CountTask(String text, int score, String answer) {
        super(text, score);
        this.answerValue = answer;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    @Override
    public void answer(String answer) {
        if (this.answerValue.equals(answer)) {
            score = maxScore;
            state = ACCEPTED;
        }
        else {
            state = NOT_ACCEPTED;
            score = 0;
        }
    }

    public void setAnswerValue(String answer) {
        this.answerValue = answer;
    }

    @NonNull
    @Override
    public String toString() {
        return "CountTask{" +
                "state=" + state +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", maxScore=" + maxScore +
                ", answerValue='" + answerValue + '\'' +
                '}';
    }
}
