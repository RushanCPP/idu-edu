package com.example.solidcourse.creating.task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.solidcourse.R;
import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.dataClasses.course.tasks.StudyTask;
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
        StringBuilder builder = new StringBuilder(task.getText());
        if (task instanceof CountTask) {
            builder.append("\n\nОтвет: ").append(((CountTask) task).getAnswerValue());
        }
        binding.fragmentTaskText.setText(builder.toString());
        binding.fragmentTaskScore.setText(String.format(Locale.getDefault(),
                "%d/%d", task.getScore(), task.getMaxScore()));
        binding.fragmentTaskEditButton.setOnClickListener(view -> {
            if (task instanceof CountTask) {
                NavHostFragment.findNavController(this).navigate(R.id.action_taskFragment_to_countTaskEditingFragment);
            } else if (task instanceof StudyTask) {
                NavHostFragment.findNavController(this).navigate(R.id.action_taskFragment_to_studyTaskEditingFragment);
            } else {
                Log.e("ERROR_TASK_CREATING", "Тип не обрабатывается, допиши код" + task);
                Toast.makeText(getContext(), "Тип не обрабатывается, допиши код", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}