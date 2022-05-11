package com.example.solidcourse.creating.course;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.solidcourse.R;
import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.dataClasses.server.SocketAdapter;
import com.example.solidcourse.databinding.FragmentCourseBinding;

import java.net.Socket;
import java.util.List;
import java.util.Locale;

public class CourseFragment extends Fragment {
    private static final int ID_EDIT = 101;
    private static final int ID_DELETE = 102;

    CourseViewModel courseViewModel;
    Course course;
    int positionLongItemClick;
    ParagraphListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        course = courseViewModel.getCourseValue();

        com.example.solidcourse.databinding.FragmentCourseBinding binding = FragmentCourseBinding.inflate(inflater, container, false);
        binding.courseFragmentCourseName.setText(course.getName());
        binding.courseFragmentCourseAuthor.setText(course.getAuthor());

        assert getContext() != null;
        adapter = new ParagraphListViewAdapter(getContext(),
                R.layout.fragment_course_list_view_item, course.getParagraphs());

        binding.courseFragmentParagraphs.setAdapter(adapter);
        binding.courseFragmentParagraphs.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            courseViewModel.setParagraphValue(course.get(index));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_courseFragment_to_paragraphFragment);
        });
        binding.courseFragmentParagraphs.setOnItemLongClickListener(((adapterView, view, index, lastParam) -> {
            positionLongItemClick = index;
            registerForContextMenu(view);
            return false;
        }));


        binding.courseFragmentAddButton.setOnClickListener(view ->
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_courseFragment_to_paragraphCreatingFragment)
        );

        binding.courseFragmentEditButton.setOnClickListener(view ->
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_courseFragment_to_courseEditingFragment)
        );

        binding.courseFragmentSaveButton.setOnClickListener(view -> {
            Thread sender = new Thread(() -> {
                String serverIp = "192.168.43.244";
                try (SocketAdapter socketAdapter = new SocketAdapter(new Socket(serverIp, 8000))) {
                    socketAdapter.write(course);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            sender.start();
        });
        return binding.getRoot();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View view, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.add(Menu.NONE, ID_EDIT, Menu.NONE, "Изменить");
        menu.add(Menu.NONE, ID_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == ID_EDIT) {
            courseViewModel.setParagraphValue(course.get(positionLongItemClick));
            NavHostFragment.findNavController(this).navigate(R.id.action_courseFragment_to_paragraphEditingFragment);
        } else if (id == ID_DELETE) {
            course.remove(positionLongItemClick);
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
    }

    private static class ParagraphListViewAdapter extends ArrayAdapter<Paragraph> {
        public ParagraphListViewAdapter(@NonNull Context context, int resource, @NonNull List<Paragraph> objects) {
            super(context, resource, objects);
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Paragraph paragraph = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.fragment_course_list_view_item, null);
            }
            ((TextView) convertView.findViewById(R.id.item_name)).setText(paragraph.getName());
            ((TextView) convertView.findViewById(R.id.item_score))
                    .setText(String.format(Locale.getDefault(),"%d/%d",
                            paragraph.getScore(), paragraph.getMaxScore()));

            return convertView;
        }
    }
}