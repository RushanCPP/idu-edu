package com.example.solidcourse.education;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.solidcourse.R;
import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.databinding.FragmentEducationStudyTaskBinding;

public class EducationStudyTaskFragment extends Fragment {
    FragmentEducationStudyTaskBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EducationViewModel viewModel = new ViewModelProvider(requireActivity()).get(EducationViewModel.class);
        Task task = viewModel.getTask();
        binding = FragmentEducationStudyTaskBinding.inflate(inflater, container, false);
        binding.educationTaskText.setText(task.getText());
        binding.acceptedButton.setOnClickListener(view -> {
            // StudyTask сам знает, как ответить
            task.answer("");
            NavHostFragment.findNavController(this).popBackStack();
        });
        return binding.getRoot();
    }
}