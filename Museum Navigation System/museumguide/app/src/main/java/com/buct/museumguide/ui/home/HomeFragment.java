package com.buct.museumguide.ui.home;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.buct.museumguide.MainActivity;
import com.buct.museumguide.R;
import com.buct.museumguide.Service.MediaPlaybackService;
import com.buct.museumguide.ui.FragmentForMain.CommonList.CommonList;
import com.buct.museumguide.ui.FragmentForUsers.Login.Login;
import com.buct.museumguide.ui.map.MapGuide;
import com.youth.banner.Banner;

import java.util.List;

/*
* 系统的默认页面，直接在这里构建页面0以及跳转逻辑，该页面的显示应按fragment实现
* 计划按逻辑实现为每个主要的方式（如地图/博物馆各种信息为一个具体的信息，其他的为fragment）
* */
public class HomeFragment extends Fragment {

    private static final String TAG =HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private Banner homeBanner;
    private Button playbutton;
    private AlertDialog.Builder builder;
    private SharedPreferences Infos;
    private int count=0;//播放计数器，按一次播放，2次停止
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
         Infos = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String info=Infos.getString("info","");
        if(info.equals("")==false){
            Toast.makeText(getActivity(),info,Toast.LENGTH_SHORT).show();System.out.println(info);
        }
    }

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
        System.out.println(getActivity());

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
                    Bundle bundle = new Bundle();
                    bundle.putInt("showType", mData.viewType);
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_commonList, bundle);
                })
                .start();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(Infos.getString("user","").equals("")){
            Infos.edit().putString("user","").apply();//首次启动
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请先登录");// 设置标题
            // builder.setIcon(R.drawable.ic_launcher);//设置图标
            //builder.setMessage(msg); 为对话框设置内容
            builder.setCancelable(false);
            // 为对话框设置取消按钮
            builder.setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_login);
                }
            });
            builder.create().show();// 使用show()方法显示对话框
        }
        playbutton=getView().findViewById(R.id.button11);
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count%2==1){
                    //mediaController.getTransportControls().prepare();
                    //mediaController.getTransportControls().play();
                }else {
                   // mediaController.getTransportControls().pause();
                }
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");

    }

}
