package com.example.solidcourse.creating.lesson;

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
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.databinding.FragmentLessonEditingBinding;

public class LessonEditingFragment extends Fragment {
    FragmentLessonEditingBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        Lesson lesson = courseViewModel.getLessonValue();
        binding = FragmentLessonEditingBinding.inflate(inflater, container, false);
        binding.editLessonNameEditText.setText(lesson.getName());
        binding.editLessonButton.setOnClickListener(view -> {
            String name = binding.editLessonNameEditText.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            lesson.setName(name);
            NavHostFragment.findNavController(this).popBackStack();
        });
        return binding.getRoot();
    }
}