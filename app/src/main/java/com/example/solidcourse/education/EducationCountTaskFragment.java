package com.example.solidcourse.education;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.solidcourse.R;
import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.databinding.FragmentEducationCountTaskBinding;

public class EducationCountTaskFragment extends Fragment {
    FragmentEducationCountTaskBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EducationViewModel viewModel = new ViewModelProvider(requireActivity()).get(EducationViewModel.class);
        Task task = viewModel.getTask();

        binding = FragmentEducationCountTaskBinding.inflate(inflater, container, false);
        if (task.isAccepted()) {
            binding.labelTaskText.append(" (Решено)");
        }
        binding.educationTaskText.setText(task.getText());
        binding.acceptedButton.setOnClickListener(view -> {
            String answer = binding.answerInput.getText().toString();
            if (answer.isEmpty()) {
                Toast.makeText(getContext(), "Введите ответ!", Toast.LENGTH_SHORT).show();
                return;
            }
            task.answer(answer);
            if (task.isAccepted()) {
                Toast.makeText(getContext(), "Правильно!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Неправильно!", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}