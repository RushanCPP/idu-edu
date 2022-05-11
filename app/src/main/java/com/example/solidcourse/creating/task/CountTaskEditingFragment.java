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
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.databinding.FragmentCountTaskEditingBinding;

public class CountTaskEditingFragment extends Fragment {
    FragmentCountTaskEditingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCountTaskEditingBinding.inflate(inflater, container, false);
        CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        CountTask task = (CountTask) courseViewModel.getTask();
        binding.countTaskEditingAnswer.setText(task.getAnswerValue());
        binding.countTaskEditingScore.setText(task.getMaxScore());
        binding.countTaskEditingText.setText(task.getText());
        binding.countTaskEditingButton.setOnClickListener(view -> {
            String answerValue = binding.countTaskEditingAnswer.getText().toString(),
                   score = binding.countTaskEditingScore.getText().toString(),
                   text = binding.countTaskEditingText.getText().toString();
            if (answerValue.isEmpty() || score.isEmpty() || text.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            task.setText(text);
            task.setAnswerValue(answerValue);
            task.setMaxScore(Integer.parseInt(score));
            NavHostFragment.findNavController(this).popBackStack();
        });
        return binding.getRoot();
    }
}