package com.example.solidcourse.dataClasses.course;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 3L;


    String name;
    List<Task> tasks;

    public Lesson(String name) {
        this.name = name;
        tasks = new ArrayList<>();
    }

    public Lesson(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public int getMaxScore() {
        int ans = 0;
        for (Task task : tasks) {
            ans += task.getMaxScore();
        }
        return ans;
    }

    public int getScore() {
        int ans = 0;
        for (Task task : tasks) {
            ans += task.getScore();
        }
        return ans;
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public void remove(int index) {
        tasks.remove(index);
    }


    @NonNull
    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
