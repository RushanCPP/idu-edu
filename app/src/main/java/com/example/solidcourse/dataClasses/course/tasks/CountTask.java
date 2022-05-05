package com.example.solidcourse.dataClasses.course.tasks;

import com.example.solidcourse.dataClasses.course.AbstractTask;

public class CountTask extends AbstractTask {
    String answerValue;
    public CountTask(String text, int score, String answer) {
        super(text, score);
        this.answerValue = answer;
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
}
