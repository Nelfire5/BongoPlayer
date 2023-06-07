package com.example.bongoplayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bongoplayer.Models.ListaModel;
import com.example.bongoplayer.R;

import java.util.ArrayList;
import java.util.List;

import bongoplayerpojo.Lista;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListaModel> lista;

    public ImageAdapter(Context context, ArrayList<ListaModel> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_playlist, null);
        }

        ImageView imageView = view.findViewById(R.id.image_view);
        TextView textView = view.findViewById(R.id.text_view);

        ListaModel imagen = lista.get(position);
        if(imagen.getIcono() == 0)
        {
            imageView.setImageResource(R.drawable.icon_musica2);
        }
        else {
            imageView.setImageResource(imagen.getIcono());
        }
        textView.setText(imagen.getNombre());

        return view;
    }

    private List<ListaModel> obtenerListaDeImagenes() {
        List<ListaModel> lista = new ArrayList<>();

        // Agrega tus imágenes y nombres a la lista
        //lista.add(new ListaModel(R.drawable.imagen1, "Nombre 1"));
        //lista.add(new ListaModel(R.drawable.imagen2, "Nombre 2"));
        // ...

        return lista;
    }
}