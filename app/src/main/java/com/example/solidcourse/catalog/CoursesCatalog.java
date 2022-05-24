package com.example.solidcourse.catalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.solidcourse.R;
import com.example.solidcourse.creating.CreatingActivity;

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
            if (id == R.id.courses_catalog) {
                recreate();
            } else if (id == R.id.favourites_course) {
                Toast.makeText(this, "Любимые курсы!", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.creating_my_courses) {
                Intent intent = new Intent(this, CreatingActivity.class);
                startActivityForResult(intent, 1);
            }
            return false;
        });
    }
}