package com.example.apptask.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptask.R;

import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder>{
     ArrayList<String> listFiles;

    public FilesAdapter(ArrayList<String> listFiles) {
        this.listFiles=listFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_files,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.onBind(listFiles.get(position));
    }

    @Override
    public int getItemCount() {
        return listFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textFile=itemView.findViewById(R.id.textFileAdress);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void onBind(String s) {
            textFile.setText(s);
        }
    }
}
