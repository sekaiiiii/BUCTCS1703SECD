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

import com.buct.museumguide.R;
import com.buct.museumguide.ui.map.MuseumMapInfo;

import java.util.ArrayList;
import java.util.List;

public class MuseumInfo extends Fragment {

    private MuseumInfoViewModel mViewModel;

    public static MuseumInfo newInstance() {
        return new MuseumInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= new ViewModelProvider(this).get(MuseumInfoViewModel.class);
        View root=inflater.inflate(R.layout.museum_info_fragment, container, false);
        final ImageView imageView=root.findViewById(R.id.imageView2);
        mViewModel.getphoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap s) {
                imageView.setImageBitmap(s);
            }
        });
        final ViewPager pager=root.findViewById(R.id.page);
        List<Fragment> list=new ArrayList<>();
        list.add(new Jianjie());
        list.add(new Canguanxuzhi());
        list.add(new zhantingfenbu());
        list.add(new jiaotong());
        pager.setAdapter(new InfoAdapter(getFragmentManager(),list));
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("选择页面"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //按钮顺序为13，10，11，12
        Button button0=root.findViewById(R.id.button13);
        Button button1=root.findViewById(R.id.button10);
        Button button2=root.findViewById(R.id.button11);
        Button button3=root.findViewById(R.id.button12);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(3);
            }
        });
        //Bitmap bitmap=getH
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MuseumInfoViewModel.class);
        // TODO: Use the ViewModel
    }

}
