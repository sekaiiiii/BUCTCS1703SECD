package com.buct.museumguide.ui.map;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.buct.museumguide.MainActivity;
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
    private int count=0;
    MapView mMapView = null;
    ArrayList<MuseumMapInfo>mapinfo=new ArrayList<>();
    ArrayList<Marker>markers=new ArrayList<>();mapinfomation map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_guide);
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        //handler要提前声明
        mMapView.onCreate(savedInstanceState);
       final AMap aMap = mMapView.getMap();
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
                Gson gson = new Gson();
                map=gson.fromJson(res,mapinfomation.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<map.getDatas().getList().size();i++){
                            Double Latitude=Double.valueOf(map.getDatas().getList().get(i).getLatitude());
                            Double logitude=Double.valueOf(map.getDatas().getList().get(i).getLongitude());
                            String name=map.getDatas().getList().get(i).getName();
                            markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(Latitude,logitude)).title(name).snippet(name)));
                        }
                    }
                });
            }
        });
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                count++;
                if(count%2==1){
                    marker.showInfoWindow();
                }else{
                    Intent intent=new Intent(MapGuide.this, MainActivity.class);
                    intent.putExtra("info",marker.getTitle());
                    startActivity(intent);

                }
                return true;
            }
        };
// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
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
