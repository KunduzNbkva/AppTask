package com.example.apptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptask.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        String uid= FirebaseAuth.getInstance().getUid();
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
            FirebaseFirestore.getInstance().collection("tasks")
                    .document(uid)
                    .set(task)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(FormActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FormActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
