package com.example.solidcourse.dataClasses.course;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paragraph implements Serializable {
    private static final long serialVersionUID = 4L;

    String name;
    List<Lesson> lessons;
    long id;
    long courseId;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Paragraph(String name) {
        this.name = name;
        lessons = new ArrayList<>();
    }

    public Paragraph(String name, List<Lesson> lessons) {
        this.name = name;
        this.lessons = lessons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public int size() {
        return lessons.size();
    }

    public Lesson get(int index) {
        return lessons.get(index);
    }

    public int getMaxScore() {
        int ans = 0;
        for (Lesson lesson : lessons) {
            ans += lesson.getMaxScore();
        }
        return ans;
    }

    public int getScore() {
        int ans = 0;
        for (Lesson lesson : lessons) {
            ans += lesson.getScore();
        }
        return ans;
    }

    public void remove(int index) {
        lessons.remove(index);
    }


    @NonNull
    @Override
    public String toString() {
        return "Paragraph{" +
                "name='" + name + '\'' +
                ", lessons=" + lessons +
                '}';
    }
}
