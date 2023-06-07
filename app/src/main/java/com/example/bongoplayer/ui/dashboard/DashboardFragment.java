package com.example.bongoplayer.ui.dashboard;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.Adapters.CancionAdapter;
import com.example.bongoplayer.Models.CancionModel;
import com.example.bongoplayer.R;
import com.example.bongoplayer.databinding.FragmentDashboardBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment implements CancionAdapter.OnItemClickListener{

    private FragmentDashboardBinding binding;
    ArrayList<CancionModel> canciones = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ---------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------

        buscarCanciones();

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler);
        CancionAdapter cancionAdapter = new CancionAdapter((List<CancionModel>) canciones,getContext(), (CancionAdapter.OnItemClickListener) this);
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

    @Override
    public void onItemClick(CancionModel cancionModel) {
        Log.e("cancionModel clicked", cancionModel.toString());
    }





    public void buscarCanciones() {

        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        int id=0;

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            @SuppressLint("Range") String artista = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            @SuppressLint("Range") String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            id++;
            String string_id = String.valueOf(id);

            @SuppressLint("Range") String ruta = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            @SuppressLint("Range") long duracion = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String string_duracion = sdf.format(new Date(duracion));
            CancionModel cancionModel = new CancionModel(string_id, nombre, artista, album, string_duracion, ruta);

            if(cancionModel.getRuta().endsWith(".mp3"))
            {
                canciones.add(cancionModel);
            }
        }
    }
}