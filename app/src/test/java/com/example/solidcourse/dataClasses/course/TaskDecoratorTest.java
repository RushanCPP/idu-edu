package com.example.solidcourse.dataClasses.course;

import org.junit.Test;

public class TaskDecoratorTest {
    private static class TempTask extends AbstractTask {
        public TempTask() {
            this("test", 5);
        }

        public TempTask(String text, int score) {
            super(text, score);
        }

        @Override
        public void answer(String answer) {
            score = maxScore;
            state = ACCEPTED;
        }
    }

    @Test
    public void getText() {
        Task temp = new TempTask();
        temp = new TaskDecorator(temp) {
            @Override
            public void answer(String answer) {
                setState(NOT_VERIFIED);
            }
        };
        temp.answer("");
        assert temp.getState() == Task.NOT_VERIFIED;
    }

    @Test
    public void isAccepted() {
        Task temp = new TempTask();
        temp.answer("");
        assert temp.isAccepted();
        temp = new TempTask();
        temp = new TaskDecorator(temp) {
            @Override
            public void answer(String answer) {
                setState(NOT_VERIFIED);
            }
        };
        temp.answer("");
        assert !temp.isAccepted();
    }

    @Test
    public void getScore() {
        Task temp = new TempTask();
        assert temp.getMaxScore() == 5;
        temp = new TaskDecorator(temp) {
            @Override
            public void answer(String answer) {
                setState(NOT_VERIFIED);
            }
        };
        assert temp.getMaxScore() == 5;
    }

    @Test
    public void getMaxScore() {
        Task temp = new TempTask();
        assert temp.getMaxScore() == 5;
        temp = new TaskDecorator(temp) {
            @Override
            public void answer(String answer) {
                setState(NOT_VERIFIED);
            }
        };
        assert temp.getMaxScore() == 5;
    }

    @Test
    public void setScore() {
        Task temp = new TempTask();
        assert temp.getMaxScore() == 5;
        temp = new TaskDecorator(temp) {
            @Override
            public void answer(String answer) {
                setState(NOT_VERIFIED);
            }
        };
        temp.setScore(3);
        assert temp.getScore() == 3;
    }

    @Test
    public void setText() {
        Task temp = new TempTask();
        assert temp.getText().equals("test");
        temp = new TaskDecorator(temp) {
            @Override
            public void answer(String answer) {
                setState(NOT_VERIFIED);
            }
        };
        temp.setText("test test");
        assert temp.getText().equals("test test");

    }

    @Test
    public void restart() {
        Task temp = new TempTask();
        assert temp.getMaxScore() == 5;
        temp = new TaskDecorator(temp) {
            @Override
            public void answer(String answer) {
                setState(ACCEPTED);
                decoratedTask.answer("");
            }
        };
        temp.answer("");
        assert temp.isAccepted() && temp.getState() == Task.ACCEPTED;

        temp.restart();
        assert !(temp.isAccepted() && temp.getState() == Task.ACCEPTED);

    }

    @Test
    public void getState() {
        Task task = new TempTask();
        task = new TaskDecorator(task) {
            @Override
            public void answer(String answer) {
                decoratedTask.answer("");
                setState(NOT_VERIFIED);
            }
        };
        task.answer("");

        assert task.getState() == Task.NOT_VERIFIED;
    }

    @Test
    public void setState() {

        Task task = new TempTask();
        task.setState(Task.ACCEPTED);
        task = new TaskDecorator(task) {
            @Override
            public void answer(String answer) {
                setState(ACCEPTED);
                decoratedTask.answer("");
            }
        };

        assert task.getState() == Task.ACCEPTED;
    }

    @Test
    public void setMaxScore() {
        Task task = new TempTask();
        task.setState(Task.ACCEPTED);
        task = new TaskDecorator(task) {
            @Override
            public void answer(String answer) {
                decoratedTask.answer("");
                setState(NOT_VERIFIED);
            }
        };

        task.setMaxScore(10);
        assert task.getMaxScore() == 10;
    }

    @Test
    public void answer() {
        Task task = new TempTask();
        task.setState(Task.ACCEPTED);
        task = new TaskDecorator(task) {
            @Override
            public void answer(String answer) {
                decoratedTask.answer("");
                setState(NOT_VERIFIED);
            }
        };

        task.answer("");
        assert task.getState() == Task.NOT_VERIFIED;

    }
}