package com.example.bongoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.bongoplayer.Adapters.IconoAdapter;
import com.example.bongoplayer.Conexion.Conexion;

import java.util.ArrayList;
import java.util.List;

import bongoplayerpojo.ExcepcionBP;
import bongoplayerpojo.Lista;
import bongoplayerpojo.Usuario;
import bongoplayerpojocomunicaciones.ExcepcionBPComunicaciones;

public class CrearListaActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private ImageView iconoImageView;
    private Button aceptarButton;

    private List<Drawable> iconos;
    private int iconoSeleccionado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lista);

        // Obtener referencias a los elementos del formulario
        nombreEditText = findViewById(R.id.nombreEditText);
        iconoImageView = findViewById(R.id.iconoImageView);
        aceptarButton = findViewById(R.id.aceptarButton);

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

        // Mostrar el primer icono por defecto
        iconoImageView.setImageDrawable(iconos.get(iconoSeleccionado));

        // Configurar el spinner para seleccionar el icono
        Spinner iconoSpinner = findViewById(R.id.iconoSpinner);
        IconoAdapter iconoAdapter = new IconoAdapter(this, iconos);
        iconoSpinner.setAdapter(iconoAdapter);
        iconoSpinner.setSelection(iconoSeleccionado);
        iconoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Actualizar el icono seleccionado cuando se cambia la selección del spinner
                iconoSeleccionado = position;
                iconoImageView.setImageDrawable(iconos.get(iconoSeleccionado));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Configurar el botón de aceptar
        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new hiloNuevaLista().execute();
            }
        });

    }

    public class hiloNuevaLista extends AsyncTask<String,Void, Usuario> {
        @Override
        protected Usuario doInBackground(String... strings) {
            try {
                Conexion conexion = new Conexion();

                String nombre = nombreEditText.getText().toString();
                String icono = String.valueOf(iconoSeleccionado);

                Usuario usuario = new Usuario(1);
                Lista lista = new Lista(null,nombre,icono,null,usuario);
                Log.e("nueva Lista", lista.toString());
                int ra = conexion.insertarLista(lista);
                if(ra == 1 )
                {
                    Intent intent = new Intent(CrearListaActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            } catch (ExcepcionBP e) {
                throw new RuntimeException(e);
            } catch (ExcepcionBPComunicaciones e) {
                throw new RuntimeException(e);
            }
            return null;
        }

    }
}