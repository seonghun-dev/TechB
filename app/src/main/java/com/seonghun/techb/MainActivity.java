package com.seonghun.techb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView iv_image;
    RecyclerView list_recyclerview;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Splash Setting
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        //RecycleView Setting
        list_recyclerview = (RecyclerView) findViewById(R.id.recycle_view);
        list_recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        int child_case = 0;
        list_recyclerview.setAdapter(new RecyclerViewAdapter(child_case, this));


        //SharedPreferences Setting
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }



}