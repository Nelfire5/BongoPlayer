package com.example.bongoplayer.ui.dashboard;

import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is music fragment");
        List<File> archivosMP3 = buscarMusica(Environment.getDownloadCacheDirectory());
        Log.e("mostrar",archivosMP3.toString());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public List<File> buscarMusica(File dir) {
        List<File> archivosMP3 = new ArrayList<>();

        // Obtener todos los archivos y subdirectorios en el directorio actual
        File[] archivos = dir.listFiles();

        // Si no hay archivos, salir de la función
        if (archivos == null) {
            return archivosMP3;
        }

        // Iterar sobre los archivos y subdirectorios encontrados
        for (File archivo : archivos) {
            if (archivo.isDirectory()) {
                // Si es un directorio, buscar archivos MP3 dentro del directorio
                archivosMP3.addAll(buscarMusica(archivo));
            } else {
                // Si es un archivo, verificar si es un archivo MP3
                String nombreArchivo = archivo.getName();
                if (nombreArchivo.endsWith(".mp3")) {
                    archivosMP3.add(archivo);
                }
            }
        }

        return archivosMP3;
    }
}