package com.example.bongoplayer.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.Environment;

import java.io.File;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}