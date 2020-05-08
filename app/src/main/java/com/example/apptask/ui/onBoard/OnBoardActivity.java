package com.example.apptask.ui.onBoard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.apptask.MainActivity;
import com.example.apptask.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;


public class OnBoardActivity extends AppCompatActivity  {
    WormDotsIndicator wormDotsIndicator;
    private ValueAnimator valueAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        final ViewPager viewPager=findViewById(R.id.viewPager);
        valueAnimator=ValueAnimator.ofObject(new ArgbEvaluator(), Color.MAGENTA,Color.CYAN,Color.YELLOW);
        wormDotsIndicator =  findViewById(R.id.worm_dots_indicator);
        viewPager.setAdapter(new SectionPagersAdapter(getSupportFragmentManager()));
        wormDotsIndicator.setViewPager(viewPager);

        valueAnimator.setDuration((3-1)*1000000000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                valueAnimator.setCurrentPlayTime((long)((position + positionOffset))*1000000000);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewPager.setBackgroundColor((Integer)animation.getAnimatedValue());
            }
        });
    }

    public void onSkip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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
