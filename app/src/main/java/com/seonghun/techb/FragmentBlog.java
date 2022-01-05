package com.seonghun.techb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

public class FragmentBlog extends Fragment {
    RecyclerView list_recyclerview;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_blog, container, false);

        //RecycleView Setting
        list_recyclerview = (RecyclerView) v.findViewById(R.id.recycle_view);
        list_recyclerview.setLayoutManager(new LinearLayoutManager(v.getContext()));
        int child_case = 0;
        list_recyclerview.setAdapter(new RecyclerViewAdapter(child_case, v.getContext()));

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
        return v;
    }


}
