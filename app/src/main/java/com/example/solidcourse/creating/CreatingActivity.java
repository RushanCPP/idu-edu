package com.example.solidcourse.creating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.solidcourse.R;
import com.example.solidcourse.databinding.ActivityCreatingBinding;

public class CreatingActivity extends AppCompatActivity {
    ActivityCreatingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}