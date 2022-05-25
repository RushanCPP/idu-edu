package com.example.solidcourse.education;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.solidcourse.ActivityID;
import com.example.solidcourse.ActivityViewModel;
import com.example.solidcourse.R;

public class EducationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        assert getSupportActionBar() != null;
        ActivityViewModel viewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.main_toolbar_id);
        toolbar.setTitle("Solid Course");
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.courses_catalog) {
                setResult(ActivityID.COURSES_CATALOG.ordinal());
                finish();
            } else if (id == R.id.creating_my_courses) {
                setResult(ActivityID.CREATING_COURSE.ordinal());
                finish();
            }
            return false;
        });
    }
}