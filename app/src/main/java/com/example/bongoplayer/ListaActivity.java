package com.example.bongoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bongoplayer.Adapters.CancionAdapter;
import com.example.bongoplayer.Conexion.Conexion;
import com.example.bongoplayer.Models.CancionModel;
import com.example.bongoplayer.Models.ListaModel;
import com.example.bongoplayer.ui.dashboard.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import bongoplayerpojo.Cancion;
import bongoplayerpojo.ExcepcionBP;
import bongoplayerpojo.Lista;
import bongoplayerpojo.Lista_Cancion;
import bongoplayerpojocomunicaciones.ExcepcionBPComunicaciones;

public class ListaActivity extends AppCompatActivity {

    ImageView icono;
    ImageButton atras;
    TextView txtNombreLista;

    ListaModel lista;
    ArrayList<Lista_Cancion> lista_canciones = new ArrayList<>();
    ArrayList<CancionModel> canciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        new hiloListasCanciones().execute();

        lista = (ListaModel) getIntent().getSerializableExtra("lista");

        icono = findViewById(R.id.imageViewLista);
        atras = findViewById(R.id.atras);
        txtNombreLista = findViewById(R.id.txtNombreLista);

        txtNombreLista.setText(lista.getNombre());
        icono.setImageResource(ponerIcono(lista));

        if(lista.getId() == 1)
        {
            canciones.add(new CancionModel("Kicks","Barns Courtney - Topic","/storage/emulated/0/snaptube/download/SnapTube Audio/Kicks(MP3_128K).mp3"));
            canciones.add(new CancionModel("Findlay - Off & On (Official Video)","Findlay","/storage/emulated/0/snaptube/download/SnapTube Audio/Findlay - Off _ On (Official Video)(MP3_128K).mp3"));
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Recycler);
        CancionAdapter cancionAdapter = new CancionAdapter(canciones, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cancionAdapter);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaActivity.this, MainActivity.class);
                intent.putExtra("listaReproducir", canciones);
                startActivity(intent);
            }
        });

    }

    public class hiloListasCanciones extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {

            try {
                Conexion conexion = new Conexion();
                lista_canciones = conexion.leerListasCanciones();
                ArrayList<Cancion> auxCancion = new ArrayList<>();
                ArrayList<Lista> auxLista = new ArrayList<>();

                auxCancion = conexion.leerCanciones();
                Log.e("auxCancion",auxCancion.toString());


                for(int i=0; i<lista_canciones.size();i++)
                {
                    if(lista_canciones.get(i).getLista().equals(lista.getId()))
                    {
                        if(lista_canciones.get(i).getCancion().equals(auxCancion.get(i).getCancionId()))
                        {
                            CancionModel cancion = new CancionModel();

                            cancion.setId(String.valueOf(auxCancion.get(i).getCancionId()));
                            cancion.setNombre(auxCancion.get(i).getNombre());
                            cancion.setArtista(auxCancion.get(i).getArtista());
                            cancion.setDuracion(auxCancion.get(i).getDuracion());
                            cancion.setRuta(auxCancion.get(i).getArchivo());

                            Log.e("cancion",cancion.toString());
                            canciones.add(cancion);
                        }
                    }
                }

            } catch (ExcepcionBP e) {
                throw new RuntimeException(e);
            } catch (ExcepcionBPComunicaciones e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }




    private int ponerIcono(ListaModel lista) {

        int drawable;

        switch (lista.getIcono()){

            case 0:
                drawable = R.drawable.nota_negra;
                break;
            case 1:
                drawable = R.drawable.nota_amarilla;
                break;
            case 2:
                drawable = R.drawable.nota_naranja;
                break;
            case 3:
                drawable = R.drawable.nota_verde;
                break;
            case 4:
                drawable = R.drawable.nota_azul;
                break;
            case 5:
                drawable = R.drawable.nota_cian;
                break;
            case 6:
                drawable = R.drawable.nota_morada;
                break;
            case 7:
                drawable = R.drawable.nota_roja;
                break;
            case 8:
                drawable = R.drawable.nota_rosa;
                break;
            default:
                drawable = R.drawable.nota_negra;
                break;
        }
        return drawable;
    }
}