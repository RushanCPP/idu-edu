package com.example.solidcourse.dataClasses.course;

import com.example.solidcourse.dataClasses.course.tasks.StudyTask;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LessonTest {

    @Test
    public void testConstructor() {
        Lesson lesson;
        {
            lesson = new Lesson("test");
            assert lesson.name.equals("test");
            assert lesson.tasks != null;
        }
        {
            lesson = new Lesson("test", new ArrayList<>());
            assert lesson.name.equals("test");
            assert lesson.tasks != null;
        }
    }

    @Test
    public void getName() {
        Lesson lesson = new Lesson("Hello!");
        assert lesson.getName().equals("Hello!");
    }

    @Test
    public void setName() {
        Lesson lesson = new Lesson("Hello!");
        lesson.setName("test");
        assert lesson.getName().equals("test");
    }

    @Test
    public void getTasks() {
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) taskList.add(new StudyTask("", 10));
        Lesson lesson = new Lesson("Hello!", taskList);
        assert lesson.getTasks() == taskList;
    }

    @Test
    public void setTasks() {
        Lesson lesson = new Lesson("Hello!");
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) taskList.add(new StudyTask("", 10));
        lesson.setTasks(taskList);
        assert lesson.getTasks() == taskList;
    }

    @Test
    public void addTask() {
        Lesson lesson = new Lesson("test");
        for (int i = 0; i < 10; ++i) {
            lesson.addTask(new StudyTask("", 10));
            assert lesson.size() == i + 1;
        }
    }

    @Test
    public void getMaxScore() {
        Lesson lesson = new Lesson("test");
        long sum = 0;
        for (int i = 0; i < 10; ++i) {
            lesson.addTask(new StudyTask("", 10));
            sum += 10;
            assert lesson.getMaxScore() == sum;
        }
    }

    @Test
    public void getScore() {
        Lesson lesson = new Lesson("test");
        for (int i = 0; i < 10; ++i) {
            lesson.addTask(new StudyTask("", 10));
        }

        long score = 0;
        for (int i = 0; i < 10; ++i) {
            lesson.get(i).answer("");
            score += 10;
            assert score == lesson.getScore();
        }

        assert score == lesson.getMaxScore();
    }

    @Test
    public void get() {
        Lesson lesson = new Lesson("test");
        for (int i = 0; i < 10; ++i) {
            lesson.addTask(new StudyTask("test" + i, 10));
        }

        assert lesson.get(0).getText().equals("test0");
    }

    @Test
    public void size() {
        Lesson lesson = new Lesson("test");
        for (int i = 0; i < 10; ++i) {
            lesson.addTask(new StudyTask("", 10));
        }

        assert lesson.size() == 10;
    }

    @Test
    public void remove() {
        Lesson lesson = new Lesson("test");
        for (int i = 0; i < 10; ++i) {
            lesson.addTask(new StudyTask("test" + i, 10));
        }

        assert lesson.size() == 10;
        lesson.remove(0);
        assert lesson.size() == 9;
        assert lesson.get(0).getText().equals("test1");
    }
}