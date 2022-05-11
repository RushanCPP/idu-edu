package com.example.solidcourse.creating.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.tasks.StudyTask;
import com.example.solidcourse.databinding.FragmentStudyTaskEditingBinding;

public class StudyTaskEditingFragment extends Fragment {
    FragmentStudyTaskEditingBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudyTaskEditingBinding.inflate(inflater, container, false);
        CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        StudyTask studyTask = (StudyTask) courseViewModel.getTask();
        binding.studyTaskEditingText.setText(studyTask.getText());
        binding.studyTaskEditingScore.setText(studyTask.getMaxScore());
        binding.studyTaskEditingButton.setOnClickListener(view -> {
            String score = binding.studyTaskEditingScore.getText().toString(),
                    text = binding.studyTaskEditingText.getText().toString();
            if (score.isEmpty() || text.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            studyTask.setText(text);
            studyTask.setMaxScore(Integer.parseInt(score));
            NavHostFragment.findNavController(this).popBackStack();
        });
        return binding.getRoot();
    }
}