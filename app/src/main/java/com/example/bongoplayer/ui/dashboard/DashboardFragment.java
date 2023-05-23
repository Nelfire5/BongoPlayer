package com.example.bongoplayer.ui.dashboard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    List<Cancion> elements;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        // ---------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler);
        CancionAdapter cancionAdapter = new CancionAdapter(elementos(),getContext());
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

    public List elementos(){
        elements = new ArrayList<>();
        for(int i=1; i<=6;i++){
            elements.add(new Cancion("Cancion"+i,"xxxxxx"));
        }
        return elements;

    }



}