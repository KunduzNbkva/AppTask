package com.example.apptask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptask.models.User;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
     EditText editName;
    private final int PICK_IMAGE = 2;
    private Uri fileUri;
    private ImageView userAva;
    private ProgressBar progressBar;
    private InputStream inputImageStream;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName=findViewById(R.id.editName);
        userAva=findViewById(R.id.userAva);
        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
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
                           showImage(user.getAvatar());
                       }
                    }
                });
    }

    private void showImage(String avatar) {
        Glide.with(this).load(avatar).circleCrop().into(userAva);
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
            upload(data.getData());
        }
    }

    private void upload(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        String uid= FirebaseAuth.getInstance().getUid();
        final StorageReference reference=FirebaseStorage.getInstance()
                .getReference().child(uid+".jpg");
        UploadTask uploadTask=reference.putFile(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return reference.getDownloadUrl() ;
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                    Uri downloadUrl=task.getResult();
                    Log.e("Profile","downloadUrl"+downloadUrl);
                    updateAvatarInfo(downloadUrl);
                } else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateAvatarInfo(Uri downloadUrl) {
        String uid= FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid)
                .update("avatar",downloadUrl.toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(ProfileActivity.this, "Oшибка", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}


