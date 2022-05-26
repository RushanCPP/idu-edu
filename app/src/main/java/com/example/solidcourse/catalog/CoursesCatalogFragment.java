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
import com.example.solidcourse.database.MyCoursesDataBase;
import com.example.solidcourse.databinding.FragmentCoursesCatalogBinding;
import com.example.solidcourse.database.FavouritesCoursesDataBase;

import java.util.ArrayList;
import java.util.List;

public class CoursesCatalogFragment extends Fragment {
    FragmentCoursesCatalogBinding binding;
    FavouritesCoursesDataBase dataBase;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCoursesCatalogBinding.inflate(inflater, container, false);
        ArrayList<Course> courses = new ArrayList<>();
        assert getContext() != null;
        CourseCatalogAdapter adapter = new CourseCatalogAdapter(getContext(), R.layout.fragment_courses_catalog_list_item, courses);
        binding.coursesCatalogListView.setAdapter(adapter);
        dataBase = new FavouritesCoursesDataBase(getContext());
        Thread thread = new Thread(() -> {
            assert getActivity() != null;
            List<Course> courseList = new MyCoursesDataBase(getContext()).selectAllCourses();
            getActivity().runOnUiThread(() -> Toast.makeText(getContext(), courseList + "", Toast.LENGTH_LONG).show());
            courses.clear();
            courses.addAll(courseList);
            assert getActivity() != null;
            getActivity().runOnUiThread(adapter::notifyDataSetChanged);
        });
        thread.start();
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
            ((TextView) convertView.findViewById(R.id.item_author)).setText(course.getAuthor());
            convertView.findViewById(R.id.add_to_favourite).setOnClickListener(view ->
                    new Thread(() -> dataBase.insertCourse(course)).start());
            return convertView;
        }
    }
}