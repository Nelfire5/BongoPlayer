package com.example.bongoplayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bongoplayer.R;

import java.io.File;
import java.util.ArrayList;

public class FileAdapter extends ArrayAdapter<File> {

    private ArrayList<File> files;

    public FileAdapter(Context context, ArrayList<File> files) {
        super(context, 0, files);
        this.files = files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public File getItem(int position) {
        return files.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.fileNameTextView = convertView.findViewById(R.id.listElement);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File file = getItem(position);
        if (file != null) {
            viewHolder.fileNameTextView.setText(file.getName());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView fileNameTextView;
    }
}

