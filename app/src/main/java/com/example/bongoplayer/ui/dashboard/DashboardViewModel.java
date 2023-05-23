package com.example.bongoplayer.ui.dashboard;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RawRes;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.Adapters.CancionAdapter;
import com.example.bongoplayer.Models.Cancion;
import com.example.bongoplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    List<Cancion> elements;
    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is music fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



}