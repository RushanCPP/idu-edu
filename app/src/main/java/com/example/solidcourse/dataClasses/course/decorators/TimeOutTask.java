package com.example.solidcourse.dataClasses.course.decorators;

import com.example.solidcourse.dataClasses.course.AbstractTask;
import com.example.solidcourse.dataClasses.course.TaskDecorator;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeOutTask extends TaskDecorator {
    private Duration duration;
    private LocalDateTime localDateTime;

    public TimeOutTask(AbstractTask task, Duration duration) {
        super(task);
        this.duration = duration;
        localDateTime = LocalDateTime.now();
    }

    @Override
    public void answer(String answer) {
        if (LocalDateTime.now().isBefore(localDateTime.plus(duration))) {
            decoratedTask.answer(answer);
        } else {
            setState(NOT_ACCEPTED);
        }
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public void restart() {
        localDateTime = LocalDateTime.now();
        super.restart();
    }
}
