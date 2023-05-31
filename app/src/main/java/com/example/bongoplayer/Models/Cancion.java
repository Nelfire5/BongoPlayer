package com.example.bongoplayer.Models;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.io.Serializable;

public class Cancion implements Serializable {

    private String id;
    private String nombre;
    private String artista;
    private String album;
    private String duracion;
    private String ruta;

    public Cancion() {
    }

    public Cancion(String nombre,String artista)
    {
        this.nombre = nombre;
        this.artista = artista;
    }
    public Cancion(String id, String nombre, String artista, String album, String duracion, String ruta) {
        this.id = id;
        this.nombre = nombre;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
        this.ruta = ruta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", artista='" + artista + '\'' +
                ", album='" + album + '\'' +
                ", duracion='" + duracion + '\'' +
                ", ruta='" + ruta + '\'' +
                '}';
    }
}

