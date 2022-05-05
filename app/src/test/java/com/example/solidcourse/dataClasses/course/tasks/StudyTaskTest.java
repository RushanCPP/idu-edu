package com.example.solidcourse.dataClasses.course.tasks;

import com.example.solidcourse.dataClasses.course.Task;

import org.junit.Test;

public class StudyTaskTest {

    @Test
    public void answer() {
        Task task = new StudyTask("Тема!", 5);
        assert !task.isAccepted();

        task.answer("");
        assert task.isAccepted();
    }
}