package com.example.solidcourse.creating.task.creating;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.solidcourse.R;
import com.example.solidcourse.dataClasses.course.TaskType;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskTypeDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getActivity() != null;
        AtomicInteger typeIndex = new AtomicInteger();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(getTypes(), -1, ((dialogInterface, index) -> {
            typeIndex.set(index);
            // TODO WRITE NORM CHOICE NAME
        }))
                .setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.cancel();
            goToCreatingFragment(TaskType.values()[typeIndex.get()]);
        })
                .setNegativeButton("Отменить", ((dialogInterface, i) -> dialogInterface.cancel()));
        return builder.create();
    }

    private String[] getTypes() {
        // TODO REWRITE THIS
        String[] types = new String[TaskType.values().length];
        for (int i = 0; i < TaskType.values().length; ++i) {
            types[i] = TaskType.values()[i].toString();
        }
        return types;
    }

    private void goToCreatingFragment(TaskType type) {
        assert getParentFragment() != null;
        if (type == TaskType.STUDY_TASK) {
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_lessonFragment_to_studyTaskCreatingFragment);
        } else if (type == TaskType.COUNT_TASK){
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_lessonFragment_to_countTaskCreatingFragment);
        }
    }
}