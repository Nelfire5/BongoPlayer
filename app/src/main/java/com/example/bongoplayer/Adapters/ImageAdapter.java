package com.example.bongoplayer.Adapters;



import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bongoplayer.Models.ListaModel;
import com.example.bongoplayer.R;
import java.util.ArrayList;



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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.text_view);
            viewHolder.imageView = convertView.findViewById(R.id.image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ListaModel imagen = lista.get(position);

        viewHolder.imageView.setImageResource(ponerIcono(lista.get(position)));
        viewHolder.textView.setText(imagen.getNombre());

        return convertView;
    }

    private int ponerIcono(ListaModel lista) {

        int drawable;

        switch (lista.getIcono()){

            case 0:
                drawable = R.drawable.nota_negra;
                break;
            case 1:
                drawable = R.drawable.nota_amarilla;
                break;
            case 2:
                drawable = R.drawable.nota_naranja;
                break;
            case 3:
                drawable = R.drawable.nota_verde;
                break;
            case 4:
                drawable = R.drawable.nota_azul;
                break;
            case 5:
                drawable = R.drawable.nota_cian;
                break;
            case 6:
                drawable = R.drawable.nota_morada;
                break;
            case 7:
                drawable = R.drawable.nota_roja;
                break;
            case 8:
                drawable = R.drawable.nota_rosa;
                break;
            default:
                drawable = R.drawable.nota_negra;
                break;
        }
        return drawable;
    }

    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
    public Context getResources() {
        Context resources = null;
        return resources;
    }
}