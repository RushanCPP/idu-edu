package com.example.solidcourse.education.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.database.FavouritesCoursesDataBase;
import com.example.solidcourse.databinding.FragmentEducationCountTaskBinding;
import com.example.solidcourse.education.EducationViewModel;

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
        binding.restartButton.setOnClickListener(view -> task.restart());
        binding.acceptedButton.setOnClickListener(view -> {
            String answer = binding.answerInput.getText().toString();
            if (answer.isEmpty()) {
                Toast.makeText(getContext(), "Введите ответ!", Toast.LENGTH_SHORT).show();
                return;
            }
            task.answer(answer);
            if (task.isAccepted()) {
                Toast.makeText(getContext(), "Правильно!", Toast.LENGTH_SHORT).show();
                Runnable saver = () -> {
                    FavouritesCoursesDataBase favouritesCoursesDataBase = new FavouritesCoursesDataBase(getContext());
                    favouritesCoursesDataBase.updateCountTask((CountTask) task);
                    assert getActivity() != null;
                    getActivity().runOnUiThread(() -> NavHostFragment.findNavController(this).popBackStack());
                };
                saver.run();
            } else {
                Toast.makeText(getContext(), "Неправильно!", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}