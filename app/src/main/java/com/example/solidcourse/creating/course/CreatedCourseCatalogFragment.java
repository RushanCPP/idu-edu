package com.example.solidcourse.creating.course;

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
import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.databinding.FragmentCreatedCourseCatalogBinding;
import com.example.solidcourse.favouriteCourses.CoursesDataBase;

import java.util.List;
import java.util.Locale;

public class CreatedCourseCatalogFragment extends Fragment {
    FragmentCreatedCourseCatalogBinding binding;
    List<Course> courses;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatedCourseCatalogBinding.inflate(inflater, container, false);
        CoursesDataBase dataBase = new CoursesDataBase(getContext());
        courses = dataBase.selectAllCourses();
        assert getContext() == null;
        binding.availableCoursesCatalog.setAdapter(new CoursesListViewAdapter(getContext(), R.layout.fragment_course_list_view_item, courses));
        binding.availableCoursesCatalog.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            Course course = courses.get(index);
            CourseViewModel courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.setCourseValue(course);
            NavHostFragment.findNavController(this).navigate(R.id.action_createdCourseCatalogFragment_to_courseFragment);
        });
        return binding.getRoot();
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