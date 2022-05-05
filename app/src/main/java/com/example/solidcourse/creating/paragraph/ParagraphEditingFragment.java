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
import com.example.solidcourse.dataClasses.course.Paragraph;
import com.example.solidcourse.databinding.FragmentParagraphEditingBinding;


public class ParagraphEditingFragment extends Fragment {
    FragmentParagraphEditingBinding binding;
    CourseViewModel courseViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        Paragraph paragraph = courseViewModel.getParagraphValue();

        binding = FragmentParagraphEditingBinding.inflate(inflater, container, false);
        binding.editParagraphNameEditText.setText(paragraph.getName());

        binding.editParagraphButton.setOnClickListener(view -> {
            String name = binding.editParagraphNameEditText.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getContext(), "Введите данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            paragraph.setName(name);
            NavHostFragment.findNavController(this).popBackStack();
        });

        return binding.getRoot();
    }
}