package com.buct.museumguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import org.jetbrains.annotations.Nullable;

public class AppSlide extends AppIntro {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
               /**/ setTransformer(new AppIntroPageTransformerType.Parallax(
                         1.0,
                         -1.0,
                        2.0
                ));

                addSlide(AppIntroFragment.newInstance(
                       "Welcome...",
                        "This is the first slide of the example"
                ));
                addSlide(AppIntroFragment.newInstance(
                       "...Let's get started!",
                        "This is the last slide, I won't annoy you more :)"
                ));
                addSlide(AppIntroFragment.newInstance(
                        "...it is page3!",
                        "This is the last slide, I won't annoy you more :)"
                ));
                addSlide(AppIntroFragment.newInstance(
                        "...it is page4!",
                        "This is the last slide, I won't annoy you more :)"
                ));
        }

        @Override
        protected void onDonePressed(@Nullable Fragment currentFragment) {
                super.onDonePressed(currentFragment);
                SharedPreferences Infos = getSharedPreferences("data", Context.MODE_PRIVATE);
                Infos.edit().putBoolean("start",true).apply();
                finish();
        }

        @Override
        protected void onSkipPressed(@Nullable Fragment currentFragment) {
                super.onSkipPressed(currentFragment);
                SharedPreferences Infos = getSharedPreferences("data", Context.MODE_PRIVATE);
                Infos.edit().putBoolean("start",true).apply();
                finish();
        }
}

