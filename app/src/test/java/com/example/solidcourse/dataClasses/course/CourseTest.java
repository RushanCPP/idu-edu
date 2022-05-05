package com.example.solidcourse.dataClasses.course;


import com.example.solidcourse.dataClasses.course.tasks.StudyTask;

import org.junit.Test;

import java.util.ArrayList;

public class CourseTest {
    @Test
    public void getName() {
        Course course = new Course("test name", "test author");
        assert course.getName().equals("test name");
    }

    @Test
    public void setName() {
        Course course = new Course("test name", "test author");
        course.setName("test name 2.0");
        assert course.getName().equals("test name 2.0");
    }

    @Test
    public void getAuthor() {
        Course course = new Course("test name", "test author");
        assert course.getAuthor().equals("test author");
    }

    @Test
    public void setAuthor() {
        Course course = new Course("test name", "test author");
        course.setAuthor("author test");
        assert course.getAuthor().equals("author test");
    }

    @Test
    public void getParagraphs() {
        Course course = new Course("test name", "test author");
        assert course.getParagraphs() != null;
        course.addParagraph(new Paragraph("test"));
        assert course.getParagraphs().size() == 1;
        assert course.getParagraphs().get(0).getName().equals("test");
    }

    @Test
    public void setParagraphs() {
        Course course = new Course("test name", "test author");
        ArrayList<Paragraph> arrayList = new ArrayList<>();
        arrayList.add(new Paragraph(""));
        course.setParagraphs(arrayList);
        assert course.getParagraphs() == arrayList;
        assert course.getParagraphs().size() == 1;
    }

    @Test
    public void addParagraph() {
        Course course = new Course("test name", "test author");
        for (int i = 0; i < 3; ++i) course.addParagraph(new Paragraph(""));
        assert course.getParagraphs().size() == 3;
    }

    @Test
    public void size() {
        Course course = new Course("test name", "test author");
        for (int i = 0; i < 3; ++i) course.addParagraph(new Paragraph(""));
        assert course.size() == 3;
    }

    @Test
    public void get() {
        Course course = new Course("test name", "test author");
        for (int i = 0; i < 3; ++i) course.addParagraph(new Paragraph("test " + i));
        assert course.get(2).getName().equals("test 2");
    }

    @Test
    public void getMaxScore() {
        Course course = new Course("test name", "test author");

        for (int i = 0; i < 3; ++i) {
            course.addParagraph(new Paragraph("test " + i));
        }

        for (int i = 0; i < 3; ++i) {
            course.get(i).addLesson(new Lesson("test test " + i));
        }

        for (int i = 0; i < 3; ++i) {
            course.get(i).get(0).addTask(new StudyTask("", 5));
        }

        assert course.getMaxScore() == 15;
    }

    @Test
    public void getScore() {
        Course course = new Course("test name", "test author");
        for (int i = 0; i < 3; ++i) {
            course.addParagraph(new Paragraph("test " + i));
        }
        for (int i = 0; i < 3; ++i) {
            course.get(i).addLesson(new Lesson("test test " + i));
        }
        for (int i = 0; i < 3; ++i) {
            course.get(i).get(0).addTask(new StudyTask("", 5));
        }


        assert course.getScore() == 0;
        course.get(0).get(0).get(0).answer("");
        assert course.getScore() == 5;
    }

    @Test
    public void remove() {
        Course course = new Course("test name", "test author");
        for (int i = 0; i < 3; ++i) {
            course.addParagraph(new Paragraph("test " + i));
        }
        for (int i = 0; i < 3; ++i) {
            course.get(i).addLesson(new Lesson("test test " + i));
        }
        for (int i = 0; i < 3; ++i) {
            course.get(i).get(0).addTask(new StudyTask("", 5));
        }

        assert course.getMaxScore() == 15;
        course.remove(0);
        assert course.getMaxScore() == 10;
        assert course.get(0).getName().equals("test 1");
    }
}