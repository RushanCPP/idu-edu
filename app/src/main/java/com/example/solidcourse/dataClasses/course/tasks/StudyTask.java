package com.example.solidcourse.dataClasses.course.tasks;

import com.example.solidcourse.dataClasses.course.AbstractTask;

public class StudyTask extends AbstractTask {
    public StudyTask(String text, int score) {
        super(text, score);
    }

    @Override
    public void answer(String answer) {
        score = maxScore;
        state = ACCEPTED;
    }
}
