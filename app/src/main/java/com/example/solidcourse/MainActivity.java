package com.example.solidcourse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.solidcourse.creating.CreatingActivity;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.creating.MyCoursesDataBase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
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
                Toast.makeText(this, "Каталог курсов!", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.favourites_course) {
                Toast.makeText(this, "Любимые курсы!", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.creating_my_courses) {
                Intent intent = new Intent(this, CreatingActivity.class);
                startActivityForResult(intent, 1);
            }
            return false;
        });

        findViewById(R.id.button123).setOnClickListener(view -> {
            MyCoursesDataBase myCoursesDataBase = new MyCoursesDataBase(this);
            List<Course> courseList = myCoursesDataBase.selectAllCourses();
            Toast.makeText(this, "" + courseList, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Toast.makeText(this, "" + ActivityID.values()[resultCode], Toast.LENGTH_SHORT).show();
        }
    }
}