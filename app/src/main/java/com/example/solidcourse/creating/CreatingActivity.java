package com.example.solidcourse.creating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.solidcourse.ActivityID;
import com.example.solidcourse.R;
import com.example.solidcourse.databinding.ActivityCreatingBinding;

public class CreatingActivity extends AppCompatActivity {
    ActivityCreatingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        binding.mainToolbarId.setTitle("Solid Course");
        binding.mainToolbarId.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.courses_catalog) {
                setResult(ActivityID.COURSES_CATALOG.ordinal());
                finish();
            } else if (id == R.id.favourites_course) {
                setResult(ActivityID.FAVOURITE_COURSES.ordinal());
                finish();
            }
            return false;
        });
    }
}