package com.example.apptask.ui.firestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apptask.R;
import com.example.apptask.models.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class FireStoreAdapter extends RecyclerView.Adapter<FireStoreAdapter.ViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    OnClick onItemClickListener;

    public void onItemClick(OnClick onItemClickListener) {
      this.onItemClickListener=onItemClickListener;
    }

    public FireStoreAdapter() {
    }

    public void updateList(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_firestore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, desc;
        FireStoreAdapter adapter;
        FirebaseFirestore db;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.textTitleFS);
            desc = itemView.findViewById(R.id.textDescFS);
            db=FirebaseFirestore.getInstance();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
        public void bind(Task task) {
            if (task != null) {
                title.setText(task.getTitle());
                desc.setText(task.getDesc());
            }
    }
}}
