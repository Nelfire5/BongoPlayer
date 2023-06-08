package com.example.bongoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bongoplayer.Models.CancionModel;
import com.example.bongoplayer.ui.dashboard.DashboardFragment;

import java.io.IOException;

public class ReproductorActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer = new MediaPlayer();
    private SeekBar seekBar;
    private boolean isSeekBarTracking = false;
    ImageButton playPausa,bucle,stop,atras;
    TextView txtNombre;
    TextView txtArtista;
    int repetir;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mediaPlayer.stop();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        CancionModel cancion = (CancionModel) getIntent().getSerializableExtra("cancion");
        Log.e("cancion ReproductorActivity", cancion.toString());

        try {
            mediaPlayer.setDataSource(cancion.getRuta());
            mediaPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        atras = findViewById(R.id.atras);
        playPausa = findViewById(R.id.buttonPlayPause);
        bucle = findViewById(R.id.buttonBucle);
        stop = findViewById(R.id.buttonStop);

        txtArtista = findViewById(R.id.txtArtista);
        txtNombre= findViewById(R.id.txtCancion);
        seekBar = findViewById(R.id.seekBar);

        txtNombre.setText(cancion.getNombre());
        txtArtista.setText(cancion.getNombre());

        txtArtista.setSelected(true);
        txtNombre.setSelected(true);

        playPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mediaPlayer.isPlaying())
                    {
                        pausa(view);
                    } else{
                        play(view);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        bucle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repetir(view);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    stop(view);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                Intent intent = new Intent(ReproductorActivity.this, DashboardFragment.class);
                startActivity(intent);
            }
        });

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekBarTracking = false;
            }
        });

    }



    public void play(View view) throws IOException {

        mediaPlayer.start();
        playPausa.setImageResource(R.drawable.pausa);
        Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        updateSeekBar();
    }

    public void pausa(View view)
    {
        mediaPlayer.pause();
        playPausa.setImageResource(R.drawable.play);
        Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
    }

    public void repetir(View view) {
        if(repetir == 1)
        {
            bucle.setImageResource(R.drawable.repetiroff);
            Toast.makeText(this, "No repetir", Toast.LENGTH_SHORT).show();
            mediaPlayer.setLooping(false);
            repetir = 2;
        }
        else
        {
            bucle.setImageResource(R.drawable.repetiron);
            Toast.makeText(this, "Repetir", Toast.LENGTH_SHORT).show();
            mediaPlayer.setLooping(true);
            repetir = 1;
        }
    }

    public void stop(View view) throws IOException {
        mediaPlayer.stop();
        Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
        playPausa.setImageResource(R.drawable.play);
        seekBar.setProgress(0);
        mediaPlayer.prepare();
    }

    private void updateSeekBar() {
        // Actualizar la barra de progreso mientras la música está reproduciéndose
        if (mediaPlayer.isPlaying()) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());

            if (!isSeekBarTracking) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar();
                    }
                };
                seekBar.postDelayed(runnable, 1000);
            }
        }
    }
}