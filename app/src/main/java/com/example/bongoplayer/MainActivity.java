package com.example.bongoplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bongoplayer.Adapters.FileAdapter;
import com.example.bongoplayer.Conexion.Conexion;
import com.example.bongoplayer.Models.Cancion;
import com.example.bongoplayer.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bongoplayer.databinding.ActivityMainBinding;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ImageButton playPausa,sig,ant,bucle;
    int repetir=2,posicion;

    ArrayList<MediaPlayer> vectormp = new ArrayList<>();
    ArrayList<Cancion> canciones = new ArrayList<>();
    private ActivityMainBinding binding;
    private static final int REQUEST_PERMISSION = 1;

    TextView txtNombre;
    TextView txtArtista;
    SeekBar seekBar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // -------------------------------------------------------------------------
        // -------------------------------------------------------------------------

        checkPermissionAndListFiles();

        DashboardFragment dashboardFragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("canciones",canciones);
        dashboardFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigation_dashboard, dashboardFragment)
                .commit();

        for(int i=0; i<canciones.size();i++)
        {
            try {
                MediaPlayer x = new MediaPlayer();
                x.setDataSource(canciones.get(i).getRuta());
                vectormp.add(x);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        posicion=0;
        Log.e("vector",vectormp.toString());
        playPausa = findViewById(R.id.buttonPlayPause);
        sig = findViewById(R.id.buttonSig);
        ant = findViewById(R.id.buttonAnt);
        bucle = findViewById(R.id.bucle);

        txtNombre = findViewById(R.id.txtCancion);
        txtArtista = findViewById(R.id.txtArtista);
        seekBar = findViewById(R.id.seekBar);

        txtNombre.setText(canciones.get(posicion).getNombre());
        txtArtista.setText(canciones.get(posicion).getArtista());

        txtArtista.setSelected(true);
        txtNombre.setSelected(true);

        seekBar.setMax(vectormp.get(posicion).getDuration());

        playPausa.setOnClickListener(view -> {
            try {
                playPause(view);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//----------------
/*        Conexion x = new Conexion(15);
        x.run();*/
    }


    public void playPause(View view) throws IOException {
        if(vectormp.get(posicion).isPlaying())
        {
            vectormp.get(posicion).pause();
            playPausa.setImageResource(R.drawable.play);
            Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
        }
        else
        {
            vectormp.get(posicion).prepare();
            vectormp.get(posicion).start();
            playPausa.setImageResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
            UpdateSeekBar updateSeekBar = new UpdateSeekBar();
            handler.post(updateSeekBar);
        }
    }

    public void repetir(View view) throws IOException {
        if(repetir == 1)
        {
            bucle.setImageResource(R.drawable.repetiroff);
            Toast.makeText(this, "No repetir", Toast.LENGTH_SHORT).show();
            vectormp.get(posicion).setLooping(false);
            repetir = 2;
        }
        else
        {
            bucle.setImageResource(R.drawable.repetiron);
            Toast.makeText(this, "Repetir", Toast.LENGTH_SHORT).show();
            vectormp.get(posicion).setLooping(true);
            repetir = 1;
        }
    }

    public void siguiente(View view) throws IOException {
        if(posicion < vectormp.size() -1){
            if(vectormp.get(posicion).isPlaying()){
                vectormp.get(posicion).stop();
                posicion ++;
                vectormp.get(posicion).prepare();
                vectormp.get(posicion).start();
                txtNombre.setText(canciones.get(posicion).getNombre());
                txtArtista.setText(canciones.get(posicion).getArtista());
            }
            else{
                posicion++;
                txtNombre.setText(canciones.get(posicion).getNombre());
                txtArtista.setText(canciones.get(posicion).getArtista());
            }
        }
        else{
            Toast.makeText(this, "No hay mas ", Toast.LENGTH_SHORT).show();
            if(vectormp.get(posicion).isPlaying()){
                posicion = 0;
                vectormp.get(posicion).stop();
                posicion ++;
                vectormp.get(posicion).prepare();
                vectormp.get(posicion).start();
                txtNombre.setText(canciones.get(posicion).getNombre());
                txtArtista.setText(canciones.get(posicion).getArtista());
            }
            else{
                posicion++;
                txtNombre.setText(canciones.get(posicion).getNombre());
                txtArtista.setText(canciones.get(posicion).getArtista());
            }
        }
    }

    public void anterior(View view) throws IOException {

        if(posicion >= 1){
            if(vectormp.get(posicion).isPlaying()){
                vectormp.get(posicion).stop();
                posicion --;
                vectormp.get(posicion).prepare();
                vectormp.get(posicion).start();
                txtNombre.setText(canciones.get(posicion).getNombre());
                txtArtista.setText(canciones.get(posicion).getArtista());
            }
            else{
                posicion --;
                txtNombre.setText(canciones.get(posicion).getNombre());
                txtArtista.setText(canciones.get(posicion).getArtista());
            }
        }
        else{
            Toast.makeText(this, "No hay mas ", Toast.LENGTH_SHORT).show();
            posicion = vectormp.size();
        }
    }


    private void checkPermissionAndListFiles() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        } else {
            buscarCanciones();
        }
    }

    public void buscarCanciones() {

        ContentResolver contentResolver = getContentResolver();
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
            Cancion cancion = new Cancion(string_id, nombre, artista, album, string_duracion, ruta);

            if(cancion.getRuta().endsWith(".mp3"))
            {
                Log.e("cancion",cancion.toString());
                canciones.add(cancion);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                buscarCanciones();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UpdateSeekBar implements Runnable{
        @Override
        public void run() {
            seekBar.setProgress(vectormp.get(posicion).getCurrentPosition());
            handler.postDelayed(this,100);
        }
    }
}