package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buct.museumguide.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MuseumList extends Fragment {
    private TabLayout tabLayout;
    private MuseumListViewModel mViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.museum_list_fragment,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.museumList_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("默认排序"));
        tabLayout.addTab(tabLayout.newTab().setText("浏览次数"));
        tabLayout.addTab(tabLayout.newTab().setText("藏品数量"));
        tabLayout.addTab(tabLayout.newTab().setText("用户评价"));
        List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new DefaultFragment());
        fragments.add(new TimeFragment());
        fragments.add(new NumberFragment());
        fragments.add(new ConmentFragment());
        MuseumPagerAdapter adapter = new MuseumPagerAdapter(getChildFragmentManager(), fragments);
        final ViewPager viewPager=view.findViewById(R.id.museum_viewpager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}
