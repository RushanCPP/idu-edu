package com.example.solidcourse.education.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.solidcourse.R;
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.dataClasses.course.tasks.StudyTask;
import com.example.solidcourse.databinding.FragmentEducationLessonBinding;
import com.example.solidcourse.education.EducationViewModel;

import java.util.List;
import java.util.Locale;

public class EducationLessonFragment extends Fragment {
    FragmentEducationLessonBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EducationViewModel viewModel = new ViewModelProvider(requireActivity()).get(EducationViewModel.class);
        Lesson lesson = viewModel.getLesson();
        assert getContext() != null;
        TasksAdapter adapter = new TasksAdapter(getContext(), R.layout.fragment_course_list_view_item, lesson.getTasks());
        binding = FragmentEducationLessonBinding.inflate(inflater, container, false);
        binding.educationLessonName.setText("Название: " + lesson.getName());
        binding.tasksToEducation.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            Task taskToSend = lesson.get(index);
            viewModel.setTask(taskToSend);
            if (taskToSend instanceof CountTask) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_educationLessonFragment_to_educationCountTaskFragment);
            } else if (taskToSend instanceof StudyTask) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_educationLessonFragment_to_educationStudyTaskFragment);
            } else {
                throw new RuntimeException("Can't define type of task!");
            }
        });
        binding.tasksToEducation.setAdapter(adapter);
        return binding.getRoot();
    }

    private static class TasksAdapter extends ArrayAdapter<Task> {
        public TasksAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
            super(context, resource, objects);
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Task task = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_course_list_view_item, null);
            }
            ((TextView) convertView.findViewById(R.id.item_name)).setText(String.format(Locale.getDefault(),
                    "№%d. %s", position + 1, task.getText()));
            ((TextView) convertView.findViewById(R.id.item_score))
                    .setText(String.format(Locale.getDefault(),"%d/%d",
                            task.getScore(), task.getMaxScore()));
            return convertView;
        }
    }
}