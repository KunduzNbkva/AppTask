package com.example.apptask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apptask.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
     EditText editName;
    private final int PICK_IMAGE = 2;
    private Uri fileUri;
    private ImageView userAva;
    private InputStream inputImageStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName=findViewById(R.id.editName);
        userAva=findViewById(R.id.userAva);
       // getData();
        getData2();

    }

    private void getData2() {
        String uid= FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                       if(documentSnapshot.exists()){
                           User user=documentSnapshot.toObject(User.class);
                           editName.setText(user.getName());
                       }
                    }
                });
    }

    private void getData() {
        String uid= FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if(task.isSuccessful()) {
                          User user=task.getResult().toObject(User.class);
                         // String name=task.getResult().getString("name");
                          editName.setText(user.getName());
                      }
                    }
                });

    }

    public void onSaveClick(View view) {
        String uid= FirebaseAuth.getInstance().getUid();
        String name=editName.getText().toString().trim();
        User user=new User("Kunduz",null,18);
//        Map<String,Object> map=new HashMap<>();
//        map.put("name","Kunduz");
//        map.put("age",18);
//        map.put("android",true);
        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

    public void onGalleryOpen(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==PICK_IMAGE){
            fileUri=data.getData();
            try {
                inputImageStream=getContentResolver().openInputStream(fileUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(inputImageStream);
            userAva.setImageBitmap(selectedImage);
        }
    }
}

