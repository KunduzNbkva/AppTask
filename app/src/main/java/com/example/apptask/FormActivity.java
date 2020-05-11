package com.example.apptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptask.models.Task;

public class FormActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editDesc;
     Task editingTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if(getSupportActionBar()!=null){
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Task");}
        editTitle=findViewById(R.id.editTitle);
        editDesc=findViewById(R.id.editDesc);
        editingTask();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onClick(View view) {
        String title=editTitle.getText().toString().trim();
        String desc=editDesc.getText().toString().trim();
        Task task=new Task();
        if(editTitle.getText().toString().equals("")||editDesc.getText().toString().equals("")){
            Toast.makeText(FormActivity.this,"Заполните поля",Toast.LENGTH_LONG).show();}
           else if (editingTask!=null){
                editingTask.setTitle(title);
                editingTask.setDesc(desc);
                App.getInstance().getDatabase().taskDao().update(editingTask);
                finish();
            }else {
               task.setTitle(title);
               task.setDesc(desc);
            App.getInstance().getDatabase().taskDao().insert(task);
            finish();
//        Intent intent=new Intent();
//        intent.putExtra("Task",task);
//        setResult(RESULT_OK,intent);
        }}

    public void editingTask(){
        editingTask=(Task)getIntent().getSerializableExtra("new Task");
        if(editingTask!=null){
            editTitle.setText(editingTask.getTitle());
            editDesc.setText(editingTask.getDesc());

        }
    }

}
