package com.example.bongoplayer;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bongoplayer.Conexion.Conexion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bongoplayer.databinding.ActivityMainBinding;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    ImageButton playPausa,sig,ant,bucle;
    MediaPlayer mp;
    ImageView iv;
    int repetir=2,posicion;

    MediaPlayer vectormp[] = new MediaPlayer[5];
    private ActivityMainBinding binding;

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

        playPausa = findViewById(R.id.buttonPlayPause);
        sig = findViewById(R.id.buttonSig);
        ant = findViewById(R.id.buttonAnt);
        bucle = findViewById(R.id.bucle);

        vectormp[0] = MediaPlayer.create(this,R.raw.a);
        vectormp[1] = MediaPlayer.create(this,R.raw.b);
        vectormp[2] = MediaPlayer.create(this,R.raw.c);
        vectormp[3] = MediaPlayer.create(this,R.raw.d);
        vectormp[4] = MediaPlayer.create(this,R.raw.e);

//----------------
        Conexion x = new Conexion(15);
        x.run();
    }



    public void playPause(View view){
        if(vectormp[posicion].isPlaying())
        {
            vectormp[posicion].pause();
            playPausa.setImageResource(R.drawable.play);
            Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
        }
        else
        {
            vectormp[posicion].start();
            playPausa.setImageResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }


    public void repetir(View view){
        if(repetir == 1)
        {
            bucle.setImageResource(R.drawable.repetiroff);
            Toast.makeText(this, "No repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(false);
            repetir = 2;
        }
        else
        {
            bucle.setImageResource(R.drawable.repetiron);
            Toast.makeText(this, "Repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(true);
            repetir = 1;
        }
    }

    public void siguiente(View view){

        if(posicion < vectormp.length -1){
            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                posicion ++;
                vectormp[posicion].start();
            }
            else{
                posicion++;
            }
        }
        else{
            Toast.makeText(this, "No hay mas ", Toast.LENGTH_SHORT).show();
            if(vectormp[posicion].isPlaying()){
                posicion = 0;
                vectormp[posicion].stop();
                posicion ++;
                vectormp[posicion].start();
            }
            else{
                posicion++;
            }

        }
    }

    public void anterior(View view){


        if(posicion >= 1){
            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                vectormp[0] = MediaPlayer.create(this,R.raw.a);
                vectormp[1] = MediaPlayer.create(this,R.raw.b);
                vectormp[2] = MediaPlayer.create(this,R.raw.c);
                vectormp[3] = MediaPlayer.create(this,R.raw.d);
                vectormp[4] = MediaPlayer.create(this,R.raw.e);
                posicion --;
                vectormp[posicion].start();
            }
            else{
                posicion --;
            }
        }
        else{
            Toast.makeText(this, "No hay mas ", Toast.LENGTH_SHORT).show();
            posicion = vectormp.length;
        }
    }

    public void cancionActual(){



    }
}