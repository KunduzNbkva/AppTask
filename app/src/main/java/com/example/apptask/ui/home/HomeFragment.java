       package com.example.apptask.ui.home;
       import android.app.Activity;
       import android.content.Intent;
       import android.os.Bundle;
       import android.util.AttributeSet;
       import android.util.Log;
       import android.view.LayoutInflater;
       import android.view.MenuItem;
       import android.view.View;
       import android.view.ViewGroup;
       import android.widget.Toast;
       import android.widget.Toolbar;

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

           private static TaskAdapter taskAdapter;
           private static List<Task> list = new ArrayList<>();
           private int pos;
           private static List<Task> sortedList;
           private static   List<Task> notSortedList;

           public View onCreateView(@NonNull LayoutInflater inflater,
                                    ViewGroup container, Bundle savedInstanceState) {
               return inflater.inflate(R.layout.fragment_home, container, false);
           }

           @Override
           public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
               super.onViewCreated(view, savedInstanceState);
               RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
               LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
               layoutManager.setReverseLayout(true);
               layoutManager.setStackFromEnd(true);
               recyclerView.setLayoutManager(layoutManager);
               list.addAll(App.getInstance().getDatabase().taskDao().getAll());
               taskAdapter = new TaskAdapter(list);
               recyclerView.setAdapter(taskAdapter);
               taskAdapter.onItemClickListener(new OnItemClickListener() {
                   @Override
                   public void onItemClick(int position) {
                       Task task = list.get(position);
                       Intent intent = new Intent(getContext(), FormActivity.class);
                       intent.putExtra("new Task", task);
                       startActivity(intent);
                   }
               });
               loadData();
               getSortedList();


           }

           private void loadData() {
               App.getInstance().getDatabase().taskDao()
                       .getAllLive()
                       .observe(getViewLifecycleOwner(), new Observer<List<Task>>() {//слушает бд и изменения
                           @Override
                           public void onChanged(List<Task> tasks) {
                               notSortedList=tasks;
                               list.clear();//очитска списка а не бд
                               list.addAll(tasks);
                               taskAdapter.notifyDataSetChanged();
                           }
                       });
           }
           private void getSortedList() {
               App.getInstance().getDatabase().taskDao().getSortedList().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
               @Override
               public void onChanged(List<Task> tasks) {
                   sortedList = tasks;
               }
           });
           }
           public static void setNotSortedList(){
              list.clear();
               list.addAll(notSortedList);
               taskAdapter.notifyDataSetChanged();
           }

           public static void setSortedList() {
               list.clear();
               list.addAll(sortedList);
               taskAdapter.notifyDataSetChanged();
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