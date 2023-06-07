package com.example.bongoplayer.Models;

import android.widget.ImageView;

public class ListaModel {

    private String nombre;
    private int icono;
    private String Usuario_ID;

    public ListaModel() {
    }

    public ListaModel(String nombre, int icono, String usuario_ID) {
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

    @Override
    public String toString() {
        return "ListaModel{" +
                "nombre='" + nombre + '\'' +
                ", icono='" + icono + '\'' +
                ", Usuario_ID='" + Usuario_ID + '\'' +
                '}';
    }
}
