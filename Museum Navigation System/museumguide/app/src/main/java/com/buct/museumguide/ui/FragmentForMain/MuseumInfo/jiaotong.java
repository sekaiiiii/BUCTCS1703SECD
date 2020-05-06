package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.buct.museumguide.R;
//拼音的这几个用于给viewpaper使用，直接切换
public class jiaotong extends Fragment {

    private JiaotongViewModel mViewModel;

    public static jiaotong newInstance() {
        return new jiaotong();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.jiaotong_fragment, container, false);
        Button button=root.findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
//设置导航功能
//将功能Scheme以URI的方式传入data
                Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&amp;poiname=fangheng&amp;lat=36.547901&amp;lon=104.258354&amp;dev=1&amp;style=2");
                intent.setData(uri);
//启动该页面即可
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(JiaotongViewModel.class);
        // TODO: Use the ViewModel
    }

}
