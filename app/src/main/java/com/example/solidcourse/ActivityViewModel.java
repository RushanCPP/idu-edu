package com.example.solidcourse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityViewModel extends ViewModel {
    final MutableLiveData<ActivityID> activityIDMutableLiveData = new MutableLiveData<>(ActivityID.FAVOURITE_COURSES);
    public ActivityID getValue() {
        return activityIDMutableLiveData.getValue();
    }

    public void setValue(ActivityID value) {
        activityIDMutableLiveData.setValue(value);
    }
}
