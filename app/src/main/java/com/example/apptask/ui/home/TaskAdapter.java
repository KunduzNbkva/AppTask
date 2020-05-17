package com.example.apptask.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptask.App;
import com.example.apptask.OnItemClickListener;
import com.example.apptask.R;
import com.example.apptask.models.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    public List<Task> list;
    public OnItemClickListener onItemClickListener;

    public void onItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        if(position %2 ==0) {
            Log.e("position","position"+position);
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<Task> tasks) {
        list = tasks;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textDesc;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textDesc = itemView.findViewById(R.id.text_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                     dialogBuilder();
                    return true;
                }
            });

        }

        public void dialogBuilder() {
            AlertDialog.Builder window = new AlertDialog.Builder(itemView.getContext());
            window.setTitle("Удаление");
            window.setMessage("Вы действительно хотите удалить запись?");
            window.setPositiveButton("Да!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    App.getInstance().getDatabase().taskDao().Delete(list.get(getAdapterPosition()));
                    notifyDataSetChanged();
                }
            });
            window.setNegativeButton("НЕТ!", null);
            window.show();
        }

            public void onBind (Task task){
                textTitle.setText(task.getTitle());
                textDesc.setText(task.getDesc());

        }
    }}
