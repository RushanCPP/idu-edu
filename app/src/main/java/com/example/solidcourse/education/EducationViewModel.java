package com.example.solidcourse.education;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.dataClasses.course.Task;

public class EducationViewModel extends ViewModel {
    private final MutableLiveData<Course> courseMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Paragraph> paragraphMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Lesson> lessonMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Task> taskMutableLiveData = new MutableLiveData<>();

    public Course getCourse() {
        return courseMutableLiveData.getValue();
    }

    public Paragraph getParagraph() {
        return paragraphMutableLiveData.getValue();
    }

    public Lesson getLesson() {
        return lessonMutableLiveData.getValue();
    }

    public Task getTask() {
        return taskMutableLiveData.getValue();
    }

    public void setCourse(Course course) {
        courseMutableLiveData.setValue(course);
    }

    public void setParagraph(Paragraph paragraph) {
        paragraphMutableLiveData.setValue(paragraph);
    }

    public void setLesson(Lesson lesson) {
        lessonMutableLiveData.setValue(lesson);
    }

    public void setTask(Task task) {
        taskMutableLiveData.setValue(task);
    }
}
