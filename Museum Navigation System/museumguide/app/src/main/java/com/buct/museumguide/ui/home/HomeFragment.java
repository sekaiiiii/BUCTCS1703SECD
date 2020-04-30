package com.buct.museumguide.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.buct.museumguide.R;
import com.buct.museumguide.ui.News.ImageNetAdapter;
import com.buct.museumguide.ui.News.MuseumNews;
import com.buct.museumguide.ui.map.MapGuide;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.Objects;

/*
* 系统的默认页面，直接在这里构建页面0以及跳转逻辑，该页面的显示应按fragment实现
* 计划按逻辑实现为每个主要的方式（如地图/博物馆各种信息为一个具体的信息，其他的为fragment）
* */
public class HomeFragment extends Fragment {

    private static final String TAG =HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private Banner homeBanner;

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        homeBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        homeBanner.stop();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final SearchView homeSearch=root.findViewById(R.id.homeSearch);
        homeSearch.setOnClickListener(v -> {
            // Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_searchResult);
        });
        final CardView cardViewIntro=root.findViewById(R.id.cardViewIntro);
        cardViewIntro.setOnClickListener(v -> {
//            Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_museumInfo);
        });
        final CardView cardViewComment=root.findViewById(R.id.cardViewComment);
        cardViewComment.setOnClickListener(v -> {
//            Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_userComment);
        });
        final Button button2=root.findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            // Toast.makeText(getActivity(),"666",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), MapGuide.class));
        });
        final Button homeMyComment=root.findViewById(R.id.homeMyComment);
        homeMyComment.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_myComment);
        });

        final TextView museumListButton = root.findViewById(R.id.museumList_button);
        museumListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_museumList);
            }
        });

        homeBanner = root.findViewById(R.id.homeBanner);
        homeBanner.setAdapter(new HomeBannerAdapter(getContext() ,MuseumItem.getTestData()))
//                .setIndicator(new CircleIndicator(getContext()))
                .setOnBannerListener((data, position) -> {
                    MuseumItem mData = (MuseumItem) data;
                    View mView = getView();
                    assert mView != null;
                    switch (mData.viewType) {
                        case 1:
                            Navigation.findNavController(mView).navigate(R.id.action_navigation_home_to_ExhibitionList);
                            break;
                        case 2:
                            Navigation.findNavController(mView).navigate(R.id.action_navigation_home_to_CollectionList);
                            break;
                        case 3:
                            Navigation.findNavController(mView).navigate(R.id.action_navigation_home_to_NewsList);
                            break;
                        case 4:
                            Navigation.findNavController(mView).navigate(R.id.action_navigation_home_to_EducationList);
                            break;
                    }
                })
                .start();
        return root;
    }

}
