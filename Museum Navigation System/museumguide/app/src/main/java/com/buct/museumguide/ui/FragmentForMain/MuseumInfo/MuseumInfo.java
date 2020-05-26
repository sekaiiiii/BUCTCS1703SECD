package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.buct.museumguide.R;
import com.buct.museumguide.ui.home.HomeViewModel;
import com.buct.museumguide.ui.map.MuseumMapInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MuseumInfo extends Fragment {

    private MuseumInfoViewModel mViewModel;
    private HomeViewModel homeViewModel;
    private TextView museumName;
    private ImageView museumPhoto;
    TabLayout tabLayout;
    public static MuseumInfo newInstance() {
        return new MuseumInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= new ViewModelProvider(this).get(MuseumInfoViewModel.class);
        homeViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        View root=inflater.inflate(R.layout.museum_info_fragment, container, false);
        museumName = root.findViewById(R.id.textView2);
        museumPhoto = root.findViewById(R.id.imageView2);
        homeViewModel.getMuseumLivaData().observe(getViewLifecycleOwner(), museum-> {
            museumName.setText(museum.getName());
            try {
                Glide.with(this)
                        .load(getImageUrl(museum.getImage_list()))
                        .apply(new RequestOptions().error(R.drawable.emptyphoto))
                        .into(museumPhoto);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        if (root != null) {
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null) {
                parent.removeView(root);
            }
            return root;
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =new ViewModelProvider(this).get(MuseumInfoViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();
        /*final ImageView imageView=getView().findViewById(R.id.imageView2);
        mViewModel.getphoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap s) {
                imageView.setImageBitmap(s);
            }
        });*/
        final ViewPager pager=getView().findViewById(R.id.page);
        List<Fragment> list=new ArrayList<>();
        list.add(new Jianjie());
//        list.add(new Canguanxuzhi());
//        list.add(new zhantingfenbu());
        list.add(new jiaotong());
         tabLayout=getView().findViewById(R.id.tablayout_info);
        tabLayout.addTab(tabLayout.newTab().setText("博物馆简介"));
//        tabLayout.addTab(tabLayout.newTab().setText("参观须知"));
//        tabLayout.addTab(tabLayout.newTab().setText("展厅分布"));
        tabLayout.addTab(tabLayout.newTab().setText("交通位置"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.setAdapter(new InfoAdapter(getChildFragmentManager(),list));
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        System.out.println("infostart");
    }

    @Override
    public void onStop() {
        super.onStop();
        tabLayout.removeAllTabs();
        System.out.println("infostop");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("inforesume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("infodestory");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("infopause");
    }
    public String getImageUrl(JSONArray imgList) throws JSONException {
        String imgurl = "";
        if(imgList.length()==0){
            imgurl = "";
        }
        else {
            imgurl = "http://192.144.239.176:8080/" + imgList.get(0).toString();
        }
        return imgurl;
    }
}
