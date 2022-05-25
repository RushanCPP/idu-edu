package com.example.solidcourse.education.fragments;

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
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.database.FavouritesCoursesDataBase;
import com.example.solidcourse.databinding.FragmentEducationCoursesBinding;
import com.example.solidcourse.education.EducationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EducationCoursesFragment extends Fragment {
    private static final int ID_DELETE = 101;
    FragmentEducationCoursesBinding binding;
    List<Course> courses = new ArrayList<>();
    CoursesListViewAdapter adapter;
    FavouritesCoursesDataBase dataBase;
    int positionOfLongClick = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EducationViewModel viewModel = new ViewModelProvider(requireActivity()).get(EducationViewModel.class);
        binding = FragmentEducationCoursesBinding.inflate(inflater, container, false);
        assert getContext() != null;
        adapter = new CoursesListViewAdapter(getContext(), R.layout.fragment_course_list_view_item, courses);
        dataBase = new FavouritesCoursesDataBase(getContext());
        // Загрузка в отдельном потоке для того чтобы главный поток не останавливался
        Runnable runnable = () -> {
            courses.clear();
            courses.addAll(dataBase.selectAllCourses());
            assert getActivity() != null;
            getActivity().runOnUiThread(adapter::notifyDataSetChanged);
        };
        runnable.run();

        binding.coursesToEducationList.setAdapter(adapter);
        binding.coursesToEducationList.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            viewModel.setCourse(courses.get(index));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_educationCoursesFragment_to_educationCourseFragment);
        });
        binding.coursesToEducationList.setOnItemLongClickListener((adapterView, view, index, lastParam) -> {
            positionOfLongClick = index;
            registerForContextMenu(view);
            return false;
        });
        return binding.getRoot();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View view, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.add(Menu.NONE, ID_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == ID_DELETE) {
            long courseId = courses.get(positionOfLongClick).getId();
            Runnable deleter = () -> dataBase.deleteCourse(courseId);
            deleter.run();
            courses.remove(positionOfLongClick);
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

        @SuppressLint({"InflateParams", "SetTextI18n"})
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Course course = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.fragment_course_list_view_item, null);
            }
            ((TextView) convertView.findViewById(R.id.item_name)).setText("Название:\t" + course.getName() + "\n\n Автор:\t" + course.getAuthor());
            ((TextView) convertView.findViewById(R.id.item_score))
                    .setText(String.format(Locale.getDefault(),"%d/%d",
                            course.getScore(), course.getMaxScore()));
            return convertView;
        }
    }
}