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
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.databinding.FragmentCountTaskCreatingBinding;

public class CountTaskCreatingFragment extends Fragment {
    FragmentCountTaskCreatingBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CountTask countTask = new CountTask("Считай! 2 + 2", 0, "4");
        binding = FragmentCountTaskCreatingBinding.inflate(inflater, container, false);

        binding.countTaskCreatingText.setText(countTask.getText());
        binding.countTaskCreatingScore.setText("" + countTask.getMaxScore());
        binding.countTaskCreatingAnswer.setText(countTask.getAnswerValue());
        binding.countTaskCreatingButton.setOnClickListener(view -> {
            String text = binding.countTaskCreatingText.getText().toString();
            String answer = binding.countTaskCreatingAnswer.getText().toString();
            String score = binding.countTaskCreatingScore.getText().toString();
            if (text.isEmpty() || answer.isEmpty() || score.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            countTask.setText(text);
            countTask.setMaxScore(Integer.parseInt(score));
            countTask.setAnswerValue(answer);

            CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.setTask(countTask);
            Lesson lesson = courseViewModel.getLessonValue();
            lesson.addTask(countTask);

            NavHostFragment.findNavController(this).popBackStack();
        });

        return binding.getRoot();
    }
}