package com.example.solidcourse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.solidcourse.catalog.CoursesCatalog;
import com.example.solidcourse.creating.CreatingActivity;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.database.MyCoursesDataBase;
import com.example.solidcourse.education.EducationActivity;

import java.util.List;

@SuppressLint("CustomSplashScreen")
public class MainActivity extends AppCompatActivity {
    ActivityID activityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.main_toolbar_id);
        toolbar.setTitle("Solid Course");
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.courses_catalog) {
                activityID = ActivityID.COURSES_CATALOG;
            } else if (id == R.id.favourites_course) {
                activityID = ActivityID.FAVOURITE_COURSES;
            } else if (id == R.id.creating_my_courses) {
                activityID = ActivityID.CREATING_COURSE;
            }
            goToActivity(activityID);
            return false;
        });

        MyCoursesDataBase coursesDataBase = new MyCoursesDataBase(this);
        Button button = findViewById(R.id.select_database);
        button.setOnClickListener(view -> {
            List<Course> courseList = coursesDataBase.selectAllCourses();
            Toast.makeText(this, "" + courseList, Toast.LENGTH_LONG).show();
            for (int i = 0; i < courseList.size(); ++i) {
                Toast.makeText(this, "" + coursesDataBase.selectAllParagraphs(courseList.get(i).getId()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToActivity(ActivityID id) {
        if (id == ActivityID.COURSES_CATALOG) {
            Intent intent = new Intent(this, CoursesCatalog.class);
            startActivityForResult(intent, 1);
        } else if (id == ActivityID.FAVOURITE_COURSES) {
            Intent intent = new Intent(this, EducationActivity.class);
            startActivityForResult(intent, 1);
        } else if (id == ActivityID.CREATING_COURSE) {
            Intent intent = new Intent(this, CreatingActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            activityID = ActivityID.values()[resultCode];
            goToActivity(activityID);
        }
    }
}