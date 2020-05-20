package com.example.apptask.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apptask.MainActivity;
import com.example.apptask.R;
import com.example.apptask.ui.onBoard.BoardFragment;
import com.example.apptask.ui.onBoard.OnBoardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    EditText editPhone;
    EditText editCode;
    String codeSent;
    LinearLayout codeLayout;
    LinearLayout phoneLayout;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
         editPhone=findViewById(R.id.editPhone);
        editCode=findViewById(R.id.editCode);
        getSupportActionBar().hide();
        codeLayout=findViewById(R.id.layoutCode);
        phoneLayout = findViewById(R.id.phoneLayout);

        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
             @Override
             public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                 // номер подтвержден
                 Log.e("Phone","onVerificationCompleted");
                // signIn(phoneAuthCredential); автоматическая проверка кода
//                 Toast.makeText(PhoneActivity.this, "Вы уже авторизованы", Toast.LENGTH_SHORT).show();
             }
             @Override
             public void onVerificationFailed(@NonNull FirebaseException e) {
                 // есть какая та ошибка
                 Log.e("Phone","onVerificationFailed"+e.getMessage());
             }
             @Override
             public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                 super.onCodeSent(s, forceResendingToken);
                 Log.e("Phone","onCodeSent");
                   codeSent=s;
                 mResendToken = forceResendingToken;
             }
         };
    }

//    private void signIn(PhoneAuthCredential phoneAuthCredential) {
//        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    startActivity(new Intent(PhoneActivity.this, MainActivity.class));
//                } else{
//                    Log.e("Phone","Error="+task.getException().getMessage());
//                    Toast.makeText(PhoneActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    } для автоматической проверки кода

    public void onContinueClick(View view) {
        String phone=editPhone.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60, //отсчет времени
                TimeUnit.SECONDS,
                this,
                callbacks);//callback срабатывает когда приходит ответ
        phoneLayout.setVisibility(View.GONE);
        codeLayout.setVisibility(View.VISIBLE);
    }

    public void onVerifyClick(View view) {
        String code=editCode.getText().toString().trim();
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeSent,code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            startActivity(new Intent(PhoneActivity.this, MainActivity.class));

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(PhoneActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show(); }
                            }
                        }
                    });
                }
}

