package com.example.solidcourse.creating.paragraph;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.solidcourse.creating.CourseViewModel;
import com.example.solidcourse.dataClasses.course.Course;
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.databinding.FragmentParagraphCreatingBinding;

public class ParagraphCreatingFragment extends Fragment {
    FragmentParagraphCreatingBinding binding;
    CourseViewModel courseViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        Course course = courseViewModel.getCourseValue();
        binding = FragmentParagraphCreatingBinding.inflate(inflater, container, false);

        binding.createParagraphButton.setOnClickListener(view -> {
            String name = binding.createParagraphNameEditText.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            course.addParagraph(new Paragraph(name));
            NavHostFragment.findNavController(this).popBackStack();
        });

        return binding.getRoot();
    }
}