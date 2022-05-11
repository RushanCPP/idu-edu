package com.example.solidcourse.creating.lesson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.solidcourse.R;
import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.creating.task.creating.TaskTypeDialog;
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.Task;
import com.example.solidcourse.dataClasses.course.tasks.CountTask;
import com.example.solidcourse.dataClasses.course.tasks.StudyTask;
import com.example.solidcourse.databinding.FragmentLessonBinding;

import java.util.List;
import java.util.Locale;

public class LessonFragment extends Fragment {
    private static final int ID_EDIT = 101;
    private static final int ID_DELETE = 102;

    FragmentLessonBinding binding;
    CourseViewModel courseViewModel;
    Lesson lesson;
    int positionOfLongClick;
    TasksAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        lesson = courseViewModel.getLessonValue();

        binding = FragmentLessonBinding.inflate(inflater, container, false);

        assert getContext() != null;
        adapter = new TasksAdapter(getContext(), R.layout.fragment_course_list_view_item, lesson.getTasks());
        binding.lessonFragmentTasks.setAdapter(adapter);

        binding.lessonFragmentTasks.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            courseViewModel.setTask(lesson.get(index));
            NavHostFragment.findNavController(this).navigate(R.id.action_lessonFragment_to_taskFragment);
        });
        binding.lessonFragmentTasks.setOnItemLongClickListener(((adapterView, view, index, lastParam) -> {
            positionOfLongClick = index;
            registerForContextMenu(view);
            return false;
        }));

        binding.lessonFragmentAddTaskButton.setOnClickListener(view -> {
            TaskTypeDialog dialog = new TaskTypeDialog();
            dialog.show(getParentFragmentManager(), "dialog");
        });

        binding.lessonFragmentLessonName.setText(lesson.getName());
        binding.lessonFragmentLessonScore.setText(String.format(Locale.getDefault(),
                "%d/%d", lesson.getScore(), lesson.getMaxScore()));

        binding.lessonFragmentEditLessonButton.setOnClickListener(view ->
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_lessonFragment_to_lessonEditingFragment)
        );
        return binding.getRoot();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, ID_EDIT, Menu.NONE, "Изменить");
        menu.add(Menu.NONE, ID_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == ID_EDIT) {
            Task task = lesson.get(positionOfLongClick);
            Toast.makeText(getContext(), "" + task.toString(), Toast.LENGTH_SHORT).show();
            if (task instanceof CountTask) {
                courseViewModel.setTask(task);
                NavHostFragment.findNavController(this).navigate(R.id.action_lessonFragment_to_countTaskEditingFragment);
            } else if (task instanceof StudyTask) {
                courseViewModel.setTask(task);
                NavHostFragment.findNavController(this).navigate(R.id.action_lessonFragment_to_studyTaskEditingFragment);
            } else {
                Toast.makeText(getContext(), "WTF!" + task, Toast.LENGTH_SHORT).show();
                // throw new RuntimeException("Hello!");
            }
        } else if (id == ID_DELETE) {
            lesson.remove(positionOfLongClick);
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
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