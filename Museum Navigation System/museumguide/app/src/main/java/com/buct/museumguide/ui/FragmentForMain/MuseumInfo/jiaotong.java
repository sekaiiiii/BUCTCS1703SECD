package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.buct.museumguide.R;

import java.util.ArrayList;
import java.util.List;

//拼音的这几个用于给viewpaper使用，直接切换
public class jiaotong extends Fragment {
    private TextureMapView textureMapView;
    private AMap aMap;
    private MuseumInfoViewModel mViewModel;
    SharedPreferences infos;
    private boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    public static jiaotong newInstance() {
        return new jiaotong();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.jiaotong_fragment, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textureMapView = (TextureMapView) getView().findViewById(R.id.map);

        if (textureMapView != null) {
            textureMapView.onCreate(savedInstanceState);
            aMap = textureMapView.getMap();
        }
        mViewModel = new ViewModelProvider(this).get(MuseumInfoViewModel.class);
        TextView textView=getActivity().findViewById(R.id.textView4);
        // TODO: Use the ViewModel
        infos=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        String lat=infos.getString("Latitude","39.929518");
        String log=infos.getString("Longtitude","116.378653");
        String msname=infos.getString("info","中国地质博物馆");
        System.out.println("博物馆地图的名字"+msname+lat+" "+log);
        LatLng latLng = new LatLng(Double.valueOf(lat),Double.valueOf(log));
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(msname));
        aMap. moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        textView.setText(msname);
        Button button=getView().findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAvilible(getActivity(),"com.autonavi.minimap")){
                    Log.d("pos123", "onClick: "+lat+" "+log+" "+msname);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    Uri uri = Uri.parse("androidamap://viewMap?sourceApplication=appname&poiname="+msname+"&lat="+lat+"&lon="+log+"&dev=0");
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"您没有安装高德地图",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        textureMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        textureMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        textureMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        textureMapView.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
