package com.example.bongoplayer.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bongoplayer.R;

import java.util.List;

public class IconoAdapter extends ArrayAdapter<Drawable> {

    private List<Drawable> iconos;
    private LayoutInflater inflater;

    public IconoAdapter(@NonNull Context context, List<Drawable> iconos) {
        super(context, 0, iconos);
        this.iconos = iconos;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_icono, parent, false);
            holder = new ViewHolder();
            holder.iconoImageView = convertView.findViewById(R.id.iconoImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener el icono actual y mostrarlo en el ImageView
        Drawable icono = iconos.get(position);
        holder.iconoImageView.setImageDrawable(icono);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return iconos.size();
    }

    @Nullable
    @Override
    public Drawable getItem(int position) {
        return iconos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView iconoImageView;
    }
}
