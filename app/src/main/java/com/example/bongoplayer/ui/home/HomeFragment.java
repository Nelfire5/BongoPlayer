package com.example.bongoplayer.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bongoplayer.Adapters.ImageAdapter;
import com.example.bongoplayer.Conexion.Conexion;
import com.example.bongoplayer.ListaActivity;
import com.example.bongoplayer.Models.ListaModel;
import com.example.bongoplayer.R;
import com.example.bongoplayer.ReproductorActivity;
import com.example.bongoplayer.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import bongoplayerpojo.ExcepcionBP;
import bongoplayerpojo.Lista;
import bongoplayerpojocomunicaciones.ExcepcionBPComunicaciones;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private GridView gridView;
    private ImageAdapter adapter;

    ArrayList<ListaModel> listas = new ArrayList<>();

    List<Drawable> iconos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------

        // Cargar los iconos en la lista
        iconos = new ArrayList<>();
        iconos.add(getResources().getDrawable(R.drawable.nota_negra));
        iconos.add(getResources().getDrawable(R.drawable.nota_amarilla));
        iconos.add(getResources().getDrawable(R.drawable.nota_naranja));
        iconos.add(getResources().getDrawable(R.drawable.nota_verde));
        iconos.add(getResources().getDrawable(R.drawable.nota_azul));
        iconos.add(getResources().getDrawable(R.drawable.nota_cian));
        iconos.add(getResources().getDrawable(R.drawable.nota_morada));
        iconos.add(getResources().getDrawable(R.drawable.nota_roja));
        iconos.add(getResources().getDrawable(R.drawable.nota_rosa));

        new hiloListas().execute();

        gridView = root.findViewById(R.id.gridView);
        adapter = new ImageAdapter(getContext(),listas);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListaModel selectedItem = listas.get(position);

                Intent intent = new Intent(getContext(), ListaActivity.class);
                intent.putExtra("lista", selectedItem);
                startActivity(intent);
            }
        });

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
                    lista.setId(listListas.get(i).getListaId());
                    lista.setNombre(listListas.get(i).getNombreLista());

                    if(listListas.get(i).getIconoLista() != null)
                    {
                        lista.setIcono(Integer.parseInt(listListas.get(i).getIconoLista()));
                    }
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