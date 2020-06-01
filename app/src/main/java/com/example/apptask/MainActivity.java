package com.example.apptask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptask.login.PhoneActivity;
import com.example.apptask.models.Task;
import com.example.apptask.ui.home.TaskAdapter;
import com.example.apptask.ui.onBoard.OnBoardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import static com.example.apptask.ui.home.HomeFragment.setNotSortedList;
import static com.example.apptask.ui.home.HomeFragment.setSortedList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    private boolean sort;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isShown()){
            startActivity(new Intent(this,OnBoardActivity.class));
            finish();
        }
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Log.e("User","CurrentUsers Instance"+ FirebaseAuth.getInstance().getCurrentUser());
            startActivity(new Intent(this, PhoneActivity.class));
            finish();
            return;
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        updateNavHeader();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivityForResult(new Intent(MainActivity.this,FormActivity.class),100);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_firestore)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_exit:
                startActivity(new Intent(this,OnBoardActivity.class));
                finish();
                break;
            case R.id.action_sort:
                if (!sort){
                    setSortedList();
                    sort = true;
                } else {
                    setNotSortedList();
                    sort = false;
                }
                break;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 100 && data != null){
           // Task task = (Task) data.getSerializableExtra("task");
          //  Log.e("TAG", "title: " + task.getTitle());
           //Log.e("TAG", "title: " + task.getDesc());
            Fragment fragment =  getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            if(fragment!=null){
                fragment.getChildFragmentManager().getFragments().get(0).onActivityResult(requestCode,resultCode,data);
            }
        }
    }

    private boolean isShown(){
        SharedPreferences preferences=getSharedPreferences("settings", Context.MODE_PRIVATE);
        return preferences.getBoolean("isShown",false);

    }

    public void headerClick(View view) {
        NavigationView navigationView =  findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        LinearLayout header =headerView.findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this,FormActivity.class));
    }

    public  void updateNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);
         View headerView=navigationView.getHeaderView(0);
        TextView header_name=headerView.findViewById(R.id.header_name);
        ImageView header_image=headerView.findViewById(R.id.header_imageView);
        header_name.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).circleCrop().into(header_image);
    }
}
