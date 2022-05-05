package com.example.solidcourse.dataClasses.course.decorators;

import com.example.solidcourse.dataClasses.course.AbstractTask;

import org.junit.Test;

import java.time.Duration;

public class TimeOutTaskTest {
    static class SimpleAbstractTask extends AbstractTask {
        public SimpleAbstractTask() {
            super("Simple test task!", 5);
        }

        @Override
        public void answer(String answer) {
            score = maxScore;
            state = ACCEPTED;
        }
    }

    @Test
    public void testDurations() {
        TimeOutTask task = new TimeOutTask(new SimpleAbstractTask(), Duration.ofSeconds(1));
        assert task.getDuration().getSeconds() == 1;
        task.setDuration(Duration.ofSeconds(2));
        assert task.getDuration().getSeconds() == 2;
    }


    @Test
    public void testAnswer() throws InterruptedException {
        {
            TimeOutTask task = new TimeOutTask(new SimpleAbstractTask(), Duration.ofSeconds(1));
            task.answer("");
            assert task.getState() == AbstractTask.ACCEPTED;
            assert task.getDuration().getSeconds() == 1;
            task.setDuration(Duration.ofSeconds(2));
            assert task.getDuration().getSeconds() == 2;
        }
        {
            TimeOutTask task = new TimeOutTask(new SimpleAbstractTask(), Duration.ofSeconds(1));

            Thread.sleep(2000);

            task.answer("");
            System.out.println(task.getState());
            assert task.getState() != AbstractTask.ACCEPTED;
        }
    }
}