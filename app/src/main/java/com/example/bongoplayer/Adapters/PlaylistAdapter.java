package com.example.bongoplayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.Models.ListaModel;
import com.example.bongoplayer.R;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    Context context;
    private List<ListaModel> playlists;
    private OnItemClickListener value;

    public interface OnItemClickListener {
        void onItemClick(ListaModel playlist);
    }

    public PlaylistAdapter(List<ListaModel> playlists, Context context) {
        this.playlists = playlists;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    value.onItemClick(playlists.get(getAdapterPosition()));

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListaModel playlist = playlists.get(position);
        holder.imageView.setImageResource(ponerIcono(playlists.get(position)));
        holder.textView.setText(playlist.getNombre());
    }

    @Override
    public int getItemCount() {
        return playlists.size();
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
}

