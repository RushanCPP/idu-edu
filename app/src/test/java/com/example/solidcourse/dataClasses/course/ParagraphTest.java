package com.example.solidcourse.dataClasses.course;

import com.example.solidcourse.dataClasses.course.tasks.StudyTask;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParagraphTest {
    @Test
    public void testConstructor() {
        Paragraph paragraph;
        {
            paragraph = new Paragraph("");
            assert paragraph.lessons != null;
        }

        {
            paragraph = new Paragraph("", new ArrayList<>());
            assert paragraph.lessons.size() == 0;
        }

        {
            List<Lesson> lessons = new ArrayList<>();
            lessons.add(new Lesson(""));
            paragraph = new Paragraph("", lessons);
            assert paragraph.get(0) == lessons.get(0);
        }
    }

    @Test
    public void testLessons() {
        Paragraph paragraph = new Paragraph("");
        paragraph.addLesson(new Lesson(""));
        assert paragraph.size() == paragraph.lessons.size();
        assert paragraph.size() == 1;

        paragraph.remove(0);
        assert paragraph.size() == 0;
        assert paragraph.lessons.size() == 0;

        paragraph.addLesson(new Lesson("test"));
        assert paragraph.get(0).name.equals("test");
    }

    @Test
    public void testMethods() {
        Paragraph paragraph = new Paragraph("test");
        for (int i = 0; i < 4; ++i) {
            Lesson lesson = new Lesson("test" + i);
            lesson.addTask(new StudyTask("", 10));
            paragraph.addLesson(lesson);
        }
        assert paragraph.getScore() == 0;
        assert paragraph.getMaxScore() != 0;
        assert paragraph.getName().equals("test");
        paragraph.setName("test test");
        assert paragraph.getName().equals("test test");

        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Lesson lesson = new Lesson("test" + i);
            lesson.addTask(new StudyTask("", 10));
            lessons.add(lesson);
        }
        paragraph.setLessons(lessons);
        assert paragraph.size() == lessons.size();
    }
}