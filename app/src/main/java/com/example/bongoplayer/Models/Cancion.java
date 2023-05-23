package com.example.bongoplayer.Models;

import android.media.Image;
import android.provider.MediaStore;

public class Cancion {
    private String titulo;
    private String artista;
    private int duracion;

    private MediaStore.Audio.Media mp3;

    public Cancion(String titulo, String artista, int duracion, MediaStore.Audio.Media mp3) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
        this.mp3 = mp3;
    }

    public Cancion(String titulo, String artista) {
        this.titulo = titulo;
        this.artista = artista;
    }

    public Cancion(MediaStore.Audio.Media mp3) {
        this.mp3 = mp3;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public MediaStore.Audio.Media getMp3() {
        return mp3;
    }

    public void setMp3(MediaStore.Audio.Media mp3) {
        this.mp3 = mp3;
    }
}

