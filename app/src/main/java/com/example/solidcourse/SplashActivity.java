package com.example.solidcourse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.solidcourse.catalog.CoursesCatalog;
import com.example.solidcourse.creating.CreatingActivity;
import com.example.solidcourse.education.EducationActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    ActivityID activityID;
    ActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this).get(ActivityViewModel.class);
        activityID = viewModel.getValue();
        Thread goTo = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> goToActivity(activityID));
        });
        goTo.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread goTo = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> goToActivity(activityID));
        });
        goTo.start();
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
            viewModel.setValue(activityID);
        }
    }
}