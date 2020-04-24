package com.buct.museumguide.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.buct.museumguide.R;
import com.buct.museumguide.ui.map.MapGuide;

/*
* 系统的默认页面，直接在这里构建页面0以及跳转逻辑，该页面的显示应按fragment实现
* 计划按逻辑实现为每个主要的方式（如地图/博物馆各种信息为一个具体的信息，其他的为fragment）
* */
public class HomeFragment extends Fragment {

    private static final String TAG =HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /**/
        final Button button1=root.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_searchResult);
            }
        });
        final Button button2=root.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MapGuide.class));
            }
        });
        final Button button9=root.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getActivity(), MapGuide.class));
                Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_museumInfo);
            }
        });
        return root;
    }

}
