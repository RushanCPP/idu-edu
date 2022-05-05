package com.example.solidcourse.creating.lesson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.solidcourse.R;
import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.Task;
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
        binding = FragmentLessonBinding.inflate(inflater, container, false);
        lesson = courseViewModel.getLessonValue();

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
            // TODO write that
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
            // TODO write that
            NavHostFragment.findNavController(this);
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