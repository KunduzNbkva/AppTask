       package com.example.apptask.ui.home;
       import android.content.Intent;
       import android.os.Bundle;
       import android.view.LayoutInflater;
       import android.view.View;
       import android.view.ViewGroup;

       import androidx.annotation.NonNull;
       import androidx.annotation.Nullable;
       import androidx.fragment.app.Fragment;
       import androidx.recyclerview.widget.LinearLayoutManager;
       import androidx.recyclerview.widget.RecyclerView;

       import com.example.apptask.R;
       import com.example.apptask.models.Task;
       import java.util.ArrayList;

       import static android.app.Activity.RESULT_OK;

       public class HomeFragment extends Fragment {

           private TaskAdapter taskAdapter;
           private ArrayList<Task> list = new ArrayList<>();
           private int pos;

           public View onCreateView(@NonNull LayoutInflater inflater,
                                    ViewGroup container, Bundle savedInstanceState) {
               return inflater.inflate(R.layout.fragment_home, container, false);
           }

           @Override
           public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
               super.onViewCreated(view, savedInstanceState);
               RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
               recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
               taskAdapter = new TaskAdapter(list);
               recyclerView.setAdapter(taskAdapter);
           }

           @Override
           public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
               super.onActivityResult(requestCode, resultCode, data);
               if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                   Task task = (Task) data.getSerializableExtra("Task");
                   list.add(pos, task);
                   taskAdapter.update(list);
                   taskAdapter.notifyDataSetChanged(); 
               }
           }
       }