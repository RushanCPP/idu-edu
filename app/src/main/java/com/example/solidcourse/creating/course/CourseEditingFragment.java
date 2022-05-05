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

import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.databinding.FragmentCourseEditingBinding;

public class CourseEditingFragment extends Fragment {
    FragmentCourseEditingBinding binding;
    CourseViewModel courseViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        Course course = courseViewModel.getCourseValue();
        binding = FragmentCourseEditingBinding.inflate(inflater, container, false);

        binding.editCourseNameEditText.setText(course.getName());
        binding.editCourseAuthorEditText.setText(course.getAuthor());

        binding.editCourseButton.setOnClickListener(view -> {
            String name = binding.editCourseNameEditText.getText().toString(),
                   author = binding.editCourseAuthorEditText.getText().toString();
            if (name.isEmpty() || author.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }

            course.setName(name);
            course.setAuthor(author);

            NavHostFragment.findNavController(this).popBackStack();
        });

        return binding.getRoot();
    }
}