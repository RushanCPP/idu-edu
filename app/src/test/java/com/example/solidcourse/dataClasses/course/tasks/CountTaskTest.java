package com.example.solidcourse.dataClasses.course.tasks;

import static org.junit.Assert.*;

import com.example.solidcourse.dataClasses.course.Task;

import org.junit.Test;

public class CountTaskTest {
    @Test
    public void answer() {
        CountTask countTask = new CountTask("1 + 2 = ", 2, "3");
        countTask.answer("3");
        assert countTask.isAccepted();
        assert countTask.getState() == Task.ACCEPTED;
        countTask.restart();
        assert !countTask.isAccepted();
        assert countTask.getState() == Task.NOT_BEGIN;
        countTask.answer("2");
        assert !countTask.isAccepted();
        assert countTask.getState() == Task.NOT_ACCEPTED;

        countTask.answer("3");
        assert countTask.isAccepted();
        assert countTask.getState() == Task.ACCEPTED;
    }
}