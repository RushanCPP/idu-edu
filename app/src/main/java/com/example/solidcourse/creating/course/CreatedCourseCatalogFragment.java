package com.example.solidcourse.creating.course;

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
import android.widget.Toast;

import com.example.solidcourse.R;
import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.server.SocketAdapter;
import com.example.solidcourse.databinding.FragmentCreatedCourseCatalogBinding;
import com.example.solidcourse.database.MyCoursesDataBase;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class CreatedCourseCatalogFragment extends Fragment {
    private static final int ID_DELETE = 102;

    FragmentCreatedCourseCatalogBinding binding;
    List<Course> courses;
    int positionOfLongClick = 0;
    MyCoursesDataBase dataBase;
    CoursesListViewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatedCourseCatalogBinding.inflate(inflater, container, false);
        dataBase = new MyCoursesDataBase(getContext());
        courses = dataBase.selectAllCourses();
        assert getContext() != null;
        Toast.makeText(getContext(), "" + courses, Toast.LENGTH_LONG).show();
        adapter = new CoursesListViewAdapter(getContext(), R.layout.fragment_course_list_view_item, courses);
        binding.availableCoursesCatalog.setAdapter(adapter);
        binding.availableCoursesCatalog.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            Course course = courses.get(index);
            CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.setCourseValue(course);
            courseViewModel.setCreateValue(false);
            NavHostFragment.findNavController(this).navigate(R.id.action_createdCourseCatalogFragment_to_courseFragment);
        });
        binding.availableCoursesCatalog.setOnItemLongClickListener((adapterView, view, index, lastParam) -> {
            positionOfLongClick = index;
            registerForContextMenu(view);
            return false;
        });
        binding.createNewCourseButton.setOnClickListener(view -> {
            CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.setCreateValue(true);
            NavHostFragment.findNavController(this).navigate(R.id.action_createdCourseCatalogFragment_to_courseCreatingFragment);
        });
        return binding.getRoot();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, ID_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == ID_DELETE) {
            Thread senderToServer = new Thread(() -> {
                String serverIp = "192.168.43.244";

                try (SocketAdapter socketAdapter = new SocketAdapter(new Socket(serverIp, 8080))) {
                    socketAdapter.writeLine("DELETE");
                    socketAdapter.writeLong(courses.get(positionOfLongClick).getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            senderToServer.start();
            dataBase.deleteCourse(courses.get(positionOfLongClick).getId());
            adapter.notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
    }


    private static class CoursesListViewAdapter extends ArrayAdapter<Course> {
        public CoursesListViewAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
            super(context, resource, objects);
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Course course = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.fragment_course_list_view_item, null);
            }
            ((TextView) convertView.findViewById(R.id.item_name)).setText(course.getName());
            ((TextView) convertView.findViewById(R.id.item_score)).setText(course.getAuthor());
            return convertView;
        }
    }
}