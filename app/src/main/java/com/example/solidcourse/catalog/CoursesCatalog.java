package com.example.solidcourse.catalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.solidcourse.ActivityID;
import com.example.solidcourse.R;

public class CoursesCatalog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_catalog);
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.main_toolbar_id);
        toolbar.setTitle("Solid Course");
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.favourites_course) {
                setResult(ActivityID.FAVOURITE_COURSES.ordinal());
                finish();
            } else if (id == R.id.creating_my_courses) {
                setResult(ActivityID.CREATING_COURSE.ordinal());
                finish();
            }
            return false;
        });
    }
}