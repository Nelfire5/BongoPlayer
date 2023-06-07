package com.example.bongoplayer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bongoplayer.R;

import java.util.List;

import bongoplayerpojo.Lista;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private List<Lista> playlists;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Lista playlist);
    }

    public PlaylistAdapter(List<Lista> playlists, OnItemClickListener clickListener) {
        this.playlists = playlists;
        this.clickListener = clickListener;
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
                    clickListener.onItemClick(playlists.get(getAdapterPosition()));
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
        Lista playlist = playlists.get(position);
        holder.imageView.setImageResource(Integer.parseInt(playlist.getIconoLista()));
        holder.textView.setText(playlist.getNombreLista());
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }
}
