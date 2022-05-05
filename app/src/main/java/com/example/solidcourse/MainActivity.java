package com.example.solidcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.solidcourse.creating.CreatingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: DELETE ME
        Intent intent = new Intent(this, CreatingActivity.class);
        startActivity(intent);
    }
}