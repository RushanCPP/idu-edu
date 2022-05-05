package com.example.solidcourse.creating.task;

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
import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.databinding.FragmentTaskBinding;

import java.util.Locale;

public class TaskFragment extends Fragment {
    FragmentTaskBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        Task task = courseViewModel.getTask();
        binding.fragmentTaskText.setText(task.getText());
        binding.fragmentTaskScore.setText(String.format(Locale.getDefault(),
                "%d/%d", task.getScore(), task.getMaxScore()));
        binding.fragmentTaskEditButton.setOnClickListener(view -> {
            // TODO WRITE ME
            NavHostFragment.findNavController(this);
            Toast.makeText(getContext(), "Editing!", Toast.LENGTH_SHORT).show();
        });
        return binding.getRoot();
    }
}