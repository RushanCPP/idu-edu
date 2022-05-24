package com.example.solidcourse.catalog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solidcourse.R;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.server.SocketAdapter;
import com.example.solidcourse.databinding.FragmentCoursesCatalogBinding;
import com.example.solidcourse.database.FavouritesCoursesDataBase;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoursesCatalogFragment extends Fragment {
    FragmentCoursesCatalogBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoursesCatalogBinding.inflate(inflater, container, false);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("Hello", "World"));
        assert getContext() != null;
        CourseCatalogAdapter adapter = new CourseCatalogAdapter(getContext(), R.layout.fragment_courses_catalog_list_item, courses);
        Thread thread = new Thread(() -> {
            String serverIp = "192.168.43.244";
            assert getActivity() != null;
            try (SocketAdapter socketAdapter = new SocketAdapter(new Socket(serverIp, 8080))) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),  "Connection!", Toast.LENGTH_SHORT).show();
                });
                socketAdapter.writeLine("COURSES");
                courses.clear();
                courses.addAll((ArrayList<Course>) socketAdapter.readObject());
                adapter.notifyDataSetChanged();
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(),  "" + courses, Toast.LENGTH_SHORT).show());
            } catch (Exception exception) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),  "Error!", Toast.LENGTH_SHORT).show();
                });
                exception.printStackTrace();
            }
        });
        thread.start();
        binding.coursesCatalogListView.setAdapter(adapter);
        return binding.getRoot();
    }
    class CourseCatalogAdapter extends ArrayAdapter<Course> {
        public CourseCatalogAdapter(@NonNull Context context, int resource, @NonNull List<Course> objects) {
            super(context, resource, objects);
        }

        @SuppressLint("InflateParams")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Course course = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.fragment_courses_catalog_list_item,
                                null);
            }
            ((TextView) convertView.findViewById(R.id.item_name)).setText(course.getName());
            ((TextView) convertView.findViewById(R.id.item_author))
                    .setText(course.getAuthor());
            convertView.findViewById(R.id.add_to_favourite).setOnClickListener(view -> {
                new FavouritesCoursesDataBase(getContext()).insertCourse(course);
            });
            return convertView;
        }
    }
}