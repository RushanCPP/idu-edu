package com.example.solidcourse.creating;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.dataClasses.course.Task;

public class CourseViewModel extends ViewModel {
    final MutableLiveData<Course> courseMutableLiveData = new MutableLiveData<>();
    final MutableLiveData<Paragraph> paragraphMutableLiveData = new MutableLiveData<>();
    final MutableLiveData<Lesson> lessonMutableLiveData = new MutableLiveData<>();
    final MutableLiveData<Task> taskMutableLiveData = new MutableLiveData<>();

    public Course getCourseValue() {
        return courseMutableLiveData.getValue();
    }

    public void setCourseValue(Course course) {
        courseMutableLiveData.setValue(course);
    }

    public Paragraph getParagraphValue() {
        return paragraphMutableLiveData.getValue();
    }

    public void setParagraphValue(Paragraph paragraph) {
        paragraphMutableLiveData.setValue(paragraph);
    }

    public Lesson getLessonValue() {
        return lessonMutableLiveData.getValue();
    }

    public void setLessonValue(Lesson lesson) {
        lessonMutableLiveData.setValue(lesson);
    }

    public Task getTask() {
        return taskMutableLiveData.getValue();
    }

    public void setTask(Task task) {
        taskMutableLiveData.setValue(task);
    }
}
