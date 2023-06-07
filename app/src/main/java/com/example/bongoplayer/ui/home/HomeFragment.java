package com.example.bongoplayer.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.Adapters.ImageAdapter;
import com.example.bongoplayer.Conexion.Conexion;
import com.example.bongoplayer.Models.ListaModel;
import com.example.bongoplayer.R;
import com.example.bongoplayer.databinding.FragmentHomeBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bongoplayerpojo.ExcepcionBP;
import bongoplayerpojo.Lista;
import bongoplayerpojo.Usuario;
import bongoplayerpojocomunicaciones.ExcepcionBPComunicaciones;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private GridView gridView;
    private ImageAdapter adapter;

    ArrayList<ListaModel> listas = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------

        new hiloListas().execute();

        gridView = root.findViewById(R.id.gridView);
        adapter = new ImageAdapter(getContext(),listas);
        gridView.setAdapter(adapter);



        return root;
    }

    public class hiloListas extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {

            try {
                Conexion conexion = new Conexion();
                ArrayList<Lista> listListas = conexion.leerListas();
                Log.e("listListas",listListas.toString());

                for(int i =0; i<listListas.size();i++)
                {
                    ListaModel lista = new ListaModel();
                    lista.setNombre(listListas.get(i).getNombreLista());
                    if(listListas.get(i).getIconoLista() != null)
                    {
                        lista.setIcono(Integer.parseInt(listListas.get(i).getIconoLista()));
                    }
                    lista.setUsuario_ID(String.valueOf(listListas.get(i).getUsuario().getUsuarioId()));

                    Log.e("lista",lista.toString());
                    listas.add(lista);
                }

            } catch (ExcepcionBP e) {
                throw new RuntimeException(e);
            } catch (ExcepcionBPComunicaciones e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}