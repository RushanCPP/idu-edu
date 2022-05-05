package com.example.solidcourse.creating.course;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.solidcourse.R;
import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.databinding.FragmentCourseCreatingBinding;


public class CourseCreatingFragment extends Fragment {
    FragmentCourseCreatingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCourseCreatingBinding.inflate(inflater, container, false);
        binding.createCourseButton.setOnClickListener(view -> {
            String name = binding.createCourseNameEditText.getText().toString(),
                   author = binding.createCourseAuthorEditText.getText().toString();
            if (name.isEmpty() || author.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.setCourseValue(new Course(name, author));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_courseCreatingFragment_to_courseFragment);
        });
        return binding.getRoot();
    }
}