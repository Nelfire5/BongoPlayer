package com.example.bongoplayer.Models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class ListaModel implements Serializable {

    private int id;
    private String nombre;
    private int icono;
    private String Usuario_ID;

    public ListaModel() {
    }

    public ListaModel(int id, String nombre, int icono, String usuario_ID) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
        Usuario_ID = usuario_ID;
    }

    public ListaModel(String nombre, String usuario_ID) {
        this.nombre = nombre;
        Usuario_ID = usuario_ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getUsuario_ID() {
        return Usuario_ID;
    }

    public void setUsuario_ID(String usuario_ID) {
        Usuario_ID = usuario_ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ListaModel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", icono=" + icono +
                ", Usuario_ID='" + Usuario_ID + '\'' +
                '}';
    }
}
