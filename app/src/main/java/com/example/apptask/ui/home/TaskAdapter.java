package com.example.apptask.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptask.OnItemClickListener;
import com.example.apptask.R;
import com.example.apptask.models.Task;


import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>  {
    public ArrayList<Task> list;


    public TaskAdapter(ArrayList<Task> list) {

        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update (ArrayList<Task> tasks){
        list=tasks;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView textTitle;
        private TextView textDesc;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle=itemView.findViewById(R.id.text_title);
            textDesc=itemView.findViewById(R.id.text_desc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  onItemClickListener.onItemClick(getAdapterPosition());
                }
            });

        }

        public void onBind(Task task) {
            textTitle.setText(task.getTitle());
            textDesc.setText(task.getDesc());
        }
    }
}
