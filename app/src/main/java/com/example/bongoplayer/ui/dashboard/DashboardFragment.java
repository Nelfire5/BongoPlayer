package com.example.bongoplayer.ui.dashboard;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.IntentCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.Adapters.CancionAdapter;
import com.example.bongoplayer.Models.Cancion;
import com.example.bongoplayer.ui.*;
import com.example.bongoplayer.R;
import com.example.bongoplayer.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    ArrayList<Cancion> elements = new ArrayList<>();
    Intent intent;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ---------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------

        elements.add(new Cancion("song","pepe"));

        Bundle arguments = getArguments();
        if (arguments != null) {
            elements = (ArrayList<Cancion>) arguments.getSerializable("canciones");
            Log.e("elemnets", elements.toString());
        }

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler);
        CancionAdapter cancionAdapter = new CancionAdapter(elements,getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(cancionAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}