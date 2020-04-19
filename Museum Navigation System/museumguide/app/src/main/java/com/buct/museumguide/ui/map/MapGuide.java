package com.buct.museumguide.ui.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.buct.museumguide.R;
/*
* 好像fragment比较麻烦，就直接activity了
* */
public class MapGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_guide);
    }
}
