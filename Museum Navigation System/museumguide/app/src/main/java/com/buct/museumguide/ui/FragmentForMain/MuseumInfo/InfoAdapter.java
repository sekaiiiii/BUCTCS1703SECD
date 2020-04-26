package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class InfoAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public InfoAdapter(@NonNull FragmentManager fm, List<Fragment> fragment) {
        super(fm);
        this.fragmentList=fragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
