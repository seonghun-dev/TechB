package com.seonghun.techb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentBlog fragmentBlog = new FragmentBlog();
    private FragmentConference fragmentConference = new FragmentConference();
    private FragmentMyprofile fragmentMyprofile = new FragmentMyprofile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPreference.getAttribute(getApplicationContext(), "userId") == null){
            setContentView(R.layout.activity_login);
        };
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentBlog).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());


        //Splash Setting
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.blog:
                    transaction.replace(R.id.frameLayout, fragmentBlog).commitAllowingStateLoss();
                    break;
                case R.id.conference:
                    transaction.replace(R.id.frameLayout, fragmentConference).commitAllowingStateLoss();
                    break;
                case R.id.myProfile:
                    transaction.replace(R.id.frameLayout, fragmentMyprofile).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}


