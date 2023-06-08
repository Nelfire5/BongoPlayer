package com.example.bongoplayer.ui.dashboard;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.example.bongoplayer.Conexion.Conexion;
import com.example.bongoplayer.Models.CancionModel;
import com.example.bongoplayer.R;
import com.example.bongoplayer.ReproductorActivity;
import com.example.bongoplayer.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import bongoplayerpojo.Cancion;
import bongoplayerpojo.ExcepcionBP;
import bongoplayerpojocomunicaciones.ExcepcionBPComunicaciones;

public class DashboardFragment extends Fragment implements CancionAdapter.OnItemClickListener{

    private FragmentDashboardBinding binding;
    ArrayList<Cancion> listCanciones = new ArrayList<>();
    ArrayList<CancionModel> canciones = new ArrayList<>();

    int ra=0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ---------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------

        try {
            ra = new hiloCanciones().execute().get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(ra ==1) {
            RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.Recycler);
            CancionAdapter cancionAdapter = new CancionAdapter((List<CancionModel>) canciones, getContext(), (CancionAdapter.OnItemClickListener) this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(cancionAdapter);
        }

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

        Intent intent = new Intent(getContext(), ReproductorActivity.class);
        intent.putExtra("cancion", cancionModel);
        startActivity(intent);

    }

    public class hiloCanciones extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {

            try {
                Conexion conexion = new Conexion();
                listCanciones = conexion.leerCanciones();

                canciones = new ArrayList<>();
                for (int i=0; i<listCanciones.size();i++)
                {
                    CancionModel cancion = new CancionModel();
                    cancion.setId(String.valueOf(listCanciones.get(i).getCancionId()));
                    cancion.setNombre(listCanciones.get(i).getNombre());
                    cancion.setArtista(listCanciones.get(i).getArtista());
                    cancion.setDuracion(listCanciones.get(i).getDuracion());
                    cancion.setRuta(listCanciones.get(i).getArchivo());
                    Log.e("canciones", cancion.toString());
                    canciones.add(cancion);
                }
                ra =1;
            } catch (ExcepcionBP e) {
                throw new RuntimeException(e);
            } catch (ExcepcionBPComunicaciones e) {
                throw new RuntimeException(e);
            }
            return ra;
        }
    }

}