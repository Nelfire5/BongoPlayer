package com.example.bongoplayer.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.Models.Cancion;
import com.example.bongoplayer.R;
import com.example.bongoplayer.ui.dashboard.DashboardFragment;

import java.util.List;

public class CancionAdapter  extends RecyclerView.Adapter<CancionAdapter.ViewHolder> {

    private List<Cancion> lista;
    private LayoutInflater inflater;
    private Context context;

    public CancionAdapter(List<Cancion> lista, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.lista = lista;
        this.context = context;
    }


    @NonNull
    @Override
    public CancionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_element,null);
        return new CancionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancionAdapter.ViewHolder holder, int position) {
        holder.bindData(lista.get(position));
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setItems(List<Cancion> item){ lista = item;}

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView cancion, artista;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.imgCaratula);
            cancion = itemView.findViewById(R.id.txtCancion);
            artista = itemView.findViewById(R.id.txtArtista);
        }

        void bindData(final Cancion item){
            cancion.setText(item.getTitulo());
            artista.setText(item.getArtista());
        }
    }
}
