package com.example.apptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.apptask.models.Task;

public class FormActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        if(getSupportActionBar()!=null){
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Task");}
        editTitle=findViewById(R.id.editTitle);
        editDesc=findViewById(R.id.editDesc);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onClick(View view) {
        String title=editTitle.getText().toString().trim();
        String desc=editDesc.getText().toString().trim();
        Task task=new Task(title,desc);
        Intent intent=new Intent();
        intent.putExtra("Task",task);
        setResult(RESULT_OK,intent);
        finish();

    }

}
