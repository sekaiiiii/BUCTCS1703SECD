package com.buct.museumguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroCustomLayoutFragment;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.Nullable;

public class AppSlide extends AppIntro {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
            final RxPermissions rxPermissions = new RxPermissions(this);
               /**/ setTransformer(new AppIntroPageTransformerType.Parallax(
                         1.0,
                         -1.0,
                        2.0
                ));

                addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.welcome));
                setColorDoneText(R.color.colorGreen);
            rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION).subscribe(granted -> {
                        if (true) {
                            Toast.makeText(AppSlide.this,"您已经授权定位功能",Toast.LENGTH_SHORT).show();
                            // All requested permissions are granted
                        } else {
                            finish();
                            // At least one permission is denied
                        }
                    });
        }

        @Override
        protected void onDonePressed(@Nullable Fragment currentFragment) {
                super.onDonePressed(currentFragment);
                SharedPreferences Infos = getSharedPreferences("data", Context.MODE_PRIVATE);
                Infos.edit().putBoolean("start",true).apply();
                Intent intent2=new Intent(AppSlide.this,MainActivity.class);
                startActivity(intent2);
                finish();
        }

        @Override
        protected void onSkipPressed(@Nullable Fragment currentFragment) {
                super.onSkipPressed(currentFragment);
                SharedPreferences Infos = getSharedPreferences("data", Context.MODE_PRIVATE);
                Infos.edit().putBoolean("start",true).apply();
                Intent intent2=new Intent(AppSlide.this,MainActivity.class);
                startActivity(intent2);
                finish();
        }
}

