package com.example.apptask.ui.onBoard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptask.MainActivity;
import com.example.apptask.R;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textFragment=view.findViewById(R.id.textfragment);
        TextView textFragment2=view.findViewById(R.id.textfragment2);
        ImageView imageView=view.findViewById(R.id.imageView);
        Button getStarted=view.findViewById(R.id.button_start);
        assert getArguments() != null;
        int pos=getArguments().getInt("pos");
        switch (pos) {
            case 0:
                textFragment.setText("Welcome to Task App!");
                textFragment2.setText("Task App number 1!");
                imageView.setImageResource(R.drawable.onboardone);
                break;
            case 1:
                textFragment.setText("This App will help you to organize your life! ");
                textFragment2.setText("Task App number 1!");
                imageView.setImageResource(R.drawable.onboardtwo);

                break;
            case 2:
                textFragment.setText("Organize.Enjoy.Live");
                textFragment2.setText("Task App number 1!");
                imageView.setImageResource(R.drawable.onboardthre);
                getStarted.setVisibility(View.VISIBLE);
                break;
        }
    }
}
