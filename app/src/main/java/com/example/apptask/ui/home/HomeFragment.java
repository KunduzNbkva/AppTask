       package com.example.apptask.ui.home;
       import android.content.Intent;
       import android.os.Bundle;
       import android.util.Log;
       import android.view.LayoutInflater;
       import android.view.View;
       import android.view.ViewGroup;

       import androidx.annotation.NonNull;
       import androidx.annotation.Nullable;
       import androidx.fragment.app.Fragment;
       import androidx.lifecycle.Observer;
       import androidx.recyclerview.widget.LinearLayoutManager;
       import androidx.recyclerview.widget.RecyclerView;

       import com.example.apptask.App;
       import com.example.apptask.FormActivity;
       import com.example.apptask.OnItemClickListener;
       import com.example.apptask.R;
       import com.example.apptask.models.Task;
       import java.util.ArrayList;
       import java.util.List;

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
               list.addAll(App.getInstance().getDatabase().taskDao().getAll());
               taskAdapter = new TaskAdapter(list);
               recyclerView.setAdapter(taskAdapter);
               taskAdapter.onItemClickListener(new OnItemClickListener() {
                   @Override
                   public void onItemClick(int position) {
                       Task task=list.get(position);
                       Intent intent=new Intent(getContext(),FormActivity.class);
                       intent.putExtra("new Task",task);
                       startActivity(intent);
                   }
               });


               loadData();
           }

           private void loadData() {
               App.getInstance().getDatabase().taskDao()
                       .getAllLive()
                       .observe(getViewLifecycleOwner(), new Observer<List<Task>>() {//слушает бд и изменения
                   @Override
                   public void onChanged(List<Task> tasks) {
                      list.clear();//очитска списка а не бд
                      list.addAll(tasks);
                      taskAdapter.notifyDataSetChanged();
                   }
               });
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