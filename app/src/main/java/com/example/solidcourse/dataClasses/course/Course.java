package com.example.solidcourse.dataClasses.course;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private static final long serialVersionUID = 2L;

    String name;
    String author;
    List<Paragraph> paragraphs;

    public Course(String name, String author) {
        this.name = name;
        this.author = author;
        paragraphs = new ArrayList<>();
    }

    public Course(String name, String author, List<Paragraph> paragraphs) {
        this.name = name;
        this.author = author;
        this.paragraphs = paragraphs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }


    public void addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
    }

    public int size() {
        return paragraphs.size();
    }

    public Paragraph get(int index) {
        return paragraphs.get(index);
    }

    public int getMaxScore() {
        int ans = 0;
        for (Paragraph paragraph : paragraphs) {
            ans += paragraph.getMaxScore();
        }
        return ans;
    }

    public int getScore() {
        int ans = 0;
        for (Paragraph paragraph : paragraphs) {
            ans += paragraph.getScore();
        }
        return ans;
    }

    public void remove(int position) {
        paragraphs.remove(position);
    }


    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", paragraphs=" + paragraphs +
                '}';
    }
}
