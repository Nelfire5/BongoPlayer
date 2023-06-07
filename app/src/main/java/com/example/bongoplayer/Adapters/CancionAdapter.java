package com.example.bongoplayer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.Models.CancionModel;
import com.example.bongoplayer.R;

import java.util.List;

public class CancionAdapter  extends RecyclerView.Adapter<CancionAdapter.ViewHolder> {

    private List<CancionModel> lista;
    private LayoutInflater inflater;
    private Context context;

    private OnItemClickListener value;


    public interface OnItemClickListener {
        void onItemClick(CancionModel cancionModel);
    }
    public CancionAdapter(List<CancionModel> lista, Context context, OnItemClickListener value) {
        this.inflater = LayoutInflater.from(context);
        this.lista = lista;
        this.context = context;
        this.value = value;
    }

    @NonNull
    @Override
    public CancionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_element,null);
        return new CancionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindData(lista.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value.onItemClick(lista.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setItems(List<CancionModel> item){ lista = item;}

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImage;
        TextView cancion, artista;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.imgCaratula);
            cancion = itemView.findViewById(R.id.txtCancion);
            artista = itemView.findViewById(R.id.txtArtista);

        }

        void bindData(final CancionModel item){
            cancion.setText(item.getNombre());
            artista.setText(item.getArtista());

        }
    }
}
