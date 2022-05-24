package com.example.solidcourse.education;

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
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.databinding.FragmentEducationCourseBinding;

import java.util.List;
import java.util.Locale;

public class EducationCourseFragment extends Fragment {
    FragmentEducationCourseBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EducationViewModel educationViewModel = new ViewModelProvider(requireActivity()).get(EducationViewModel.class);
        Course course = educationViewModel.getCourse();
        assert getContext() != null;
        ParagraphListViewAdapter adapter = new ParagraphListViewAdapter(getContext(),
                R.layout.fragment_course_list_view_item, course.getParagraphs());
        binding = FragmentEducationCourseBinding.inflate(inflater, container, false);
        binding.courseName.setText("Название: " + course.getName());
        binding.courseAuthor.setText("Автор: " + course.getAuthor());
        binding.paragraphsToEducation.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            educationViewModel.setParagraph(course.get(index));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_educationCourseFragment_to_educationParagraphFragment);
        });
        binding.paragraphsToEducation.setAdapter(adapter);
        return binding.getRoot();
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