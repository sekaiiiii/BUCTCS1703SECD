package com.buct.museumguide.ui.map;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.buct.museumguide.R;
import com.buct.museumguide.util.WebHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
* 好像fragment比较麻烦，就直接activity了
* */
public class MapGuide extends AppCompatActivity {
    private static final String TAG ="map" ;
    MapView mMapView = null;
    ArrayList<MuseumMapInfo>mapinfo=new ArrayList<>();
    ArrayList<Marker>markers=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_guide);
        OkHttpClient client= WebHelper.getInstance().client;
        Request request=new Request.Builder().url("http://192.144.239.176:8080/api/android/get_position")
                .addHeader("Content-Type", "application/json; charset=utf-8").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res=response.body().string();
                System.out.println(res.toString());
                Gson gson = new Gson();
                mapinfomation map=gson.fromJson(res,mapinfomation.class);
                System.out.println(map.getDatas().getList().size());
            }
        });
        //这里应使用http请求的结果来调用
        mapinfo.add(new MuseumMapInfo(39.906901,116.397972));
        mapinfo.add(new MuseumMapInfo(39.406901,116.297972));
        mapinfo.add(new MuseumMapInfo(39.106901,116.197972));

        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        AMap aMap = null;
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        for(int i=0;i<mapinfo.size();i++){
            markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(mapinfo.get(i).getLatitude(),mapinfo.get(i).getLogitude())).title(mapinfo.get(i).getTitle()).snippet("DefaultMarker")));
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
