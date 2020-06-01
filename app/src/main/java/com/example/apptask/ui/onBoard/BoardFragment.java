package com.example.apptask.ui.onBoard;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.apptask.MainActivity;
import com.example.apptask.R;
import static com.example.apptask.R.raw.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_board, container, false);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textFragment=view.findViewById(R.id.textfragment);
        TextView textFragment2=view.findViewById(R.id.textfragment2);
       // ImageView imageView=view.findViewById(R.id.imageView);
        Button getStarted=view.findViewById(R.id.button_start);
        assert getArguments() != null;
        int pos=getArguments().getInt("pos");

        switch (pos) {
            case 0:
                textFragment.setText("Welcome to Task App!");
                textFragment2.setText("Task App number 1!");
               // imageView.setImageResource(R.drawable.onboardone);
                setAnimationView(onb1);
                break;

            case 1:
                textFragment.setText("This App will help you to organize your life! ");
                textFragment2.setText("Task App number 1!");
               // imageView.setImageResource(R.drawable.onboardtwo);
                setAnimationView(onb2);
                break;
            case 2:
                textFragment.setText("Organize.Enjoy.Live");
                textFragment2.setText("Task App number 1!");
               // imageView.setImageResource(R.drawable.onboardthre);
                setAnimationView(onb3);
                getStarted.setVisibility(View.VISIBLE);
                break;
        }
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIsShown();
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
    }
    private void saveIsShown() {
        SharedPreferences preferences=getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isShown",true).apply();
    }

//    private void changeColor(){
//        ColorDrawable cd = new ColorDrawable(getActivity().getResources().getColor(
//                R.color.colorAccent));
//        getActivity().getWindow().setBackgroundDrawable(cd);
//    } меняет цвет активити
    public void setAnimationView(int s){
    LottieAnimationView animationView=getView().findViewById(R.id.animation_view);
     animationView.setAnimation(s);
    animationView.loop(true); }




}
