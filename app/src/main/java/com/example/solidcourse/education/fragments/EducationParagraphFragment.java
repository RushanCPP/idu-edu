package com.example.solidcourse.education.fragments;

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
import com.example.solidcourse.dataClasses.course.Lesson;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.databinding.FragmentEducationParagraphBinding;
import com.example.solidcourse.education.EducationViewModel;

import java.util.List;
import java.util.Locale;

public class EducationParagraphFragment extends Fragment {
    FragmentEducationParagraphBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EducationViewModel viewModel = new ViewModelProvider(requireActivity()).get(EducationViewModel.class);
        Paragraph paragraph = viewModel.getParagraph();
        assert getContext() != null;
        LessonListViewAdapter adapter = new LessonListViewAdapter(getContext(), R.layout.fragment_course_list_view_item, paragraph.getLessons());
        binding = FragmentEducationParagraphBinding.inflate(inflater, container, false);
        binding.educationParagraphName.setText("Название: " + paragraph.getName());
        binding.lessonsToEducation.setAdapter(adapter);
        binding.lessonsToEducation.setOnItemClickListener((adapterView, view, index, lastParam) -> {
            viewModel.setLesson(paragraph.get(index));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_educationParagraphFragment_to_educationLessonFragment);
        });
        return binding.getRoot();
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