package com.example.apptask.ui.onBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.apptask.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class OnBoardActivity extends AppCompatActivity {
    WormDotsIndicator wormDotsIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        ViewPager viewPager=findViewById(R.id.viewPager);
        wormDotsIndicator =  findViewById(R.id.worm_dots_indicator);
        viewPager.setAdapter(new SectionPagersAdapter(getSupportFragmentManager()));
        wormDotsIndicator.setViewPager(viewPager);
    }

    public void onSkip(View view) {
    }

    public class SectionPagersAdapter extends FragmentPagerAdapter{

        public SectionPagersAdapter(@NonNull FragmentManager fm) {
            super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Bundle bundle=new Bundle();
            bundle.putInt("pos",position);
            BoardFragment fragment=new BoardFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
