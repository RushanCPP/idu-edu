package com.example.solidcourse.creating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

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
                Toast.makeText(this, "Каталог курсов!", Toast.LENGTH_SHORT).show();
                setResult(ActivityID.CATALOG_OF_COURSES.ordinal());
                finish();
            } else if (id == R.id.favourites_course) {
                Toast.makeText(this, "Любимые курсы!", Toast.LENGTH_SHORT).show();
                setResult(ActivityID.FAVOURITE.ordinal());
                finish();
            } else if (id == R.id.creating_my_courses) {
                recreate();
            }
            return false;
        });
    }
}