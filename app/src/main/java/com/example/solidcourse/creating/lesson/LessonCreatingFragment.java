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
import com.example.solidcourse.databinding.FragmentLessonCreatingBinding;

public class LessonCreatingFragment extends Fragment {
    FragmentLessonCreatingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLessonCreatingBinding.inflate(inflater, container, false);
        binding.createLessonButton.setOnClickListener(view -> {
            String name = binding.createLessonNameEditText.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные", Toast.LENGTH_SHORT).show();
                return;
            }
            CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.getParagraphValue().addLesson(new Lesson(name));
            NavHostFragment.findNavController(this).popBackStack();
        });
        return binding.getRoot();
    }
}