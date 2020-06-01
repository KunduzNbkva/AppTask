package com.example.apptask.ui.firestore;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.apptask.FormActivity;
import com.example.apptask.R;
import com.example.apptask.models.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireStoreFragment extends Fragment {
    private FireStoreAdapter adapter;
    private List<Task> tasks;
    FirebaseFirestore dataBase;
    RecyclerView recyclerView;

    public FireStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_firestore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         recyclerView=view.findViewById(R.id.recyclerViewFireStore);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasks=new ArrayList<>();
        adapter=new FireStoreAdapter();
        recyclerView.setAdapter(adapter);
        dataBase= FirebaseFirestore.getInstance();
        dataBase.collection("tasks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List <DocumentSnapshot> documentSnapshotList=queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d:documentSnapshotList){
                        Task task=d.toObject(Task.class);
                        tasks.add(task);
                        adapter.updateList(tasks);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        adapter.onItemClick(new OnClick() {
            @Override
            public void onItemClick(int position) {
                Task task=tasks.get(position);
                Intent intent=new Intent(getContext(), FormActivity.class);
                intent.putExtra("newTask",task);
                startActivity(intent);
            }
        });
    }
}
