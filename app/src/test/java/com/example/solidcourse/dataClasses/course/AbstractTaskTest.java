package com.example.solidcourse.dataClasses.course;

import org.junit.Test;

public class AbstractTaskTest {
    @Test
    public void getText() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
            }
        };

        assert task.getText().equals("test");
    }

    @Test
    public void isAccepted() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
                state = ACCEPTED;
                score = maxScore;
            }
        };

        task.answer("");
        assert task.isAccepted();
    }

    @Test
    public void getScore() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
                score = maxScore;
            }
        };

        assert task.getScore() == 0;
        task.answer("");

        assert task.getScore() == task.getMaxScore();
    }

    @Test
    public void getMaxScore() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
            }
        };

        assert task.getMaxScore() == 3;
    }

    @Test
    public void setMaxScore() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
            }
        };
        task.setMaxScore(5);
        assert task.getMaxScore() == 5;
    }

    @Test
    public void setScore() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
            }
        };

        task.setScore(2);
        assert task.getScore() == 2;
    }

    @Test
    public void setText() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
            }
        };

        task.setText("test test");

        assert task.getText().equals("test test");
    }

    @Test
    public void getState() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
                state = ACCEPTED;
            }
        };

        assert task.getState() == Task.NOT_BEGIN;
        task.answer("");
        assert task.getState() == Task.ACCEPTED;
    }

    @Test
    public void setState() {

        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
            }
        };

        task.setState(Task.NOT_ACCEPTED);
        assert task.getState() == Task.NOT_ACCEPTED;
    }

    @Test
    public void restart() {
        Task task = new AbstractTask("test", 3) {
            @Override
            public void answer(String answer) {
                score = maxScore;
                state = ACCEPTED;
            }
        };

        task.answer("");
        assert task.isAccepted();

        task.restart();

        assert !task.isAccepted();
    }
}