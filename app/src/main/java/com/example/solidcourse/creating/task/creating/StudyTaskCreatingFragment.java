package com.example.solidcourse.creating.task.creating;

import android.annotation.SuppressLint;
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
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.tasks.StudyTask;
import com.example.solidcourse.databinding.FragmentStudyTaskCreatingBinding;

public class StudyTaskCreatingFragment extends Fragment {
    FragmentStudyTaskCreatingBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudyTaskCreatingBinding.inflate(inflater, container, false);
        StudyTask studyTask = new StudyTask("Учись!", 0);

        binding.studyTaskCreatingText.setText(studyTask.getText());
        binding.studyTaskCreatingScore.setText("" + studyTask.getMaxScore());
        binding.studyTaskCreatingButton.setOnClickListener(view -> {
            String text = binding.studyTaskCreatingText.getText().toString();
            String score = binding.studyTaskCreatingScore.getText().toString();
            if (text.isEmpty() || score.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            studyTask.setText(text);
            studyTask.setMaxScore(Integer.parseInt(score));

            CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.setTask(studyTask);
            Lesson lesson = courseViewModel.getLessonValue();
            lesson.addTask(studyTask);

            NavHostFragment.findNavController(this).popBackStack();
        });

        return binding.getRoot();
    }
}