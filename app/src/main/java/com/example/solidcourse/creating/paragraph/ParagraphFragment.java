package com.example.solidcourse.creating.paragraph;

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
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.databinding.FragmentParagraphBinding;

import java.util.List;
import java.util.Locale;

public class ParagraphFragment extends Fragment {
    private static final int ID_EDIT = 101;
    private static final int ID_DELETE = 102;

    FragmentParagraphBinding binding;
    CourseViewModel courseViewModel;
    LessonListViewAdapter adapter;
    Paragraph paragraph;
    int positionOfLongClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        paragraph = courseViewModel.getParagraphValue();

        assert getContext() != null;
        adapter = new LessonListViewAdapter(getContext(), R.layout.fragment_course_list_view_item, paragraph.getLessons());

        binding = FragmentParagraphBinding.inflate(inflater, container, false);

        binding.paragraphFragmentParagraphName.setText(paragraph.getName());
        binding.paragraphFragmentParagraphScore.setText(String.format(Locale.getDefault(),
                "%d/%d", paragraph.getScore(), paragraph.getMaxScore()));

        binding.paragraphFragmentAddButton.setOnClickListener(view ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_paragraphFragment_to_lessonCreatingFragment)
        );
        binding.paragraphFragmentEditButton.setOnClickListener(view ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_paragraphFragment_to_paragraphEditingFragment)
        );

        binding.paragraphFragmentLessons.setAdapter(adapter);

        binding.paragraphFragmentLessons.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            courseViewModel.setLessonValue(paragraph.get(index));
            NavHostFragment.findNavController(this).navigate(R.id.action_paragraphFragment_to_lessonFragment);
        });
        binding.paragraphFragmentLessons.setOnItemLongClickListener((adapterView, view, index, lastParam) -> {
            positionOfLongClick = index;
            registerForContextMenu(view);
            return false;
        });

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
            courseViewModel.setLessonValue(paragraph.get(positionOfLongClick));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_paragraphFragment_to_lessonEditingFragment);
        } else if (id == ID_DELETE) {
            paragraph.remove(positionOfLongClick);
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
    }

    private static class LessonListViewAdapter extends ArrayAdapter<Lesson> {
        public LessonListViewAdapter(@NonNull Context context, int resource, @NonNull List<Lesson> objects) {
            super(context, resource, objects);
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Lesson lesson = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.fragment_course_list_view_item, null);
            }
            ((TextView) convertView.findViewById(R.id.item_name)).setText(lesson.getName());
            ((TextView) convertView.findViewById(R.id.item_score))
                    .setText(String.format(Locale.getDefault(),"%d/%d",
                            lesson.getScore(), lesson.getMaxScore()));

            return convertView;
        }
    }
}