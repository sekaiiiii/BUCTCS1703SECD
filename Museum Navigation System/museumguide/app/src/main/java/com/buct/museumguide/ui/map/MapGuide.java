package com.buct.museumguide.ui.map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.buct.museumguide.MainActivity;
import com.buct.museumguide.R;
import com.buct.museumguide.util.FileHelper;
import com.buct.museumguide.util.MapHelper;
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
import java.util.concurrent.ConcurrentHashMap;

/*
* 好像fragment比较麻烦，就直接activity了
* */
public class MapGuide extends AppCompatActivity {
    private static final String TAG ="map" ;
    private int count=0;
    MapView mMapView = null;
    String s="";
    ArrayList<MuseumMapInfo>mapinfo=new ArrayList<>();
    ArrayList<Marker>markers=new ArrayList<>();mapinfomation map;
    ArrayList<MarkerOptions>markers1=new ArrayList<>();
    ConcurrentHashMap<String,String> hashMap=new ConcurrentHashMap<>();
    ConcurrentHashMap<String,Integer> hashMap1=new ConcurrentHashMap<>();
    SharedPreferences info;
    ArrayList<String>minestMuseum=new ArrayList<>();
    private String markerid="";
    protected View getMyView(String name) {
        View view=getLayoutInflater().inflate(R.layout.mapmarker, null);
        TextView tv_val=(TextView) view.findViewById(R.id.tv_endphone);
        tv_val.setText(name);
        return view;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        info= getSharedPreferences("data", Context.MODE_PRIVATE);
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(5000);
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_map_guide);
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        //handler要提前声明
        mMapView.onCreate(savedInstanceState);
        final AMap aMap = mMapView.getMap();
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
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
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        for(int i=0;i<map.getDatas().getList().size();i++){
                            hashMap.put(map.getDatas().getList().get(i).getName(),map.getDatas().getList().get(i).getId());
                            hashMap1.put(map.getDatas().getList().get(i).getName(),i);
                            Double Latitude=Double.valueOf(map.getDatas().getList().get(i).getLatitude());
                            Double logitude=Double.valueOf(map.getDatas().getList().get(i).getLongitude());
                            String name=map.getDatas().getList().get(i).getName();
                            markers.add(aMap.addMarker(new MarkerOptions().position(new LatLng(Latitude,logitude)).title(name)
                            .icon(BitmapDescriptorFactory.fromView(getMyView(name)))));
                            //markers1.add(new MarkerOptions().position(new LatLng(Latitude,logitude)).title(name));
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
                System.out.println(marker.getId().equals(markerid)+" "+count);
                System.out.println("博物馆经纬度"+map.getDatas().getList().get(hashMap1.get(marker.getTitle())).getLatitude()
                +map.getDatas().getList().get(hashMap1.get(marker.getTitle())).getLongitude());
                    info.edit().putString("Latitude",map.getDatas().getList().get(hashMap1.get(marker.getTitle())).getLatitude()).apply();
                info.edit().putString("Longtitude",map.getDatas().getList().get(hashMap1.get(marker.getTitle())).getLongitude()).apply();
                info.edit().putString("info",map.getDatas().getList().get(hashMap1.get(marker.getTitle())).getName()).apply();
                    Intent intent=new Intent(MapGuide.this, MainActivity.class);
                    intent.putExtra("info",marker.getTitle());
                    intent.putExtra("museumid_map",hashMap.get(marker.getTitle()));
                    startActivity(intent);
                return true;
            }
        };
// 绑定 Marker 被点击事件
        AMap.OnMyLocationChangeListener listener=new AMap.OnMyLocationChangeListener(){

            @Override
            public void onMyLocationChange(Location location) {
                s="";
                double Latitude=location.getLatitude();
                double Longtitude=location.getLongitude();
                System.out.println("定位"+Latitude+" "+Longtitude);
                List<Double>distence=new ArrayList<>();
            /**/
            if(map!=null){
                distence.clear();
                minestMuseum.clear();
                for(int i=0;i<map.getDatas().getList().size();i++){
                    double len= MapHelper.getDistance(Latitude,Longtitude
                    ,Double.valueOf(map.getDatas().getList().get(i).getLatitude()),
                            Double.valueOf(map.getDatas().getList().get(i).getLongitude() ));
                    distence.add(len);
                }
                double des=999999;
                int ismin=-1;
                for(int i=0;i<map.getDatas().getList().size();i++){
                    if(distence.get(i)<des){
                        des=distence.get(i);
                        ismin=i;
                    }
                    if(distence.get(i)<30){
                        minestMuseum.add(map.getDatas().getList().get(i).getName());
                    }
                }
                System.out.println("最近的是"+ismin+" "+map.getDatas().getList().get(ismin).getName());
                System.out.println("附近的博物馆是");

                 for(String m:minestMuseum){
                     System.out.println(m);
                     s+=m+"\n";
                 }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Toast.makeText(MapGuide.this,"您附近的博物馆有：\n" +
                                s,Toast.LENGTH_LONG).show();
                     }
                 });
            }
            }
        };
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.setOnMyLocationChangeListener(listener);
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
    protected void onStop() {
        super.onStop();
        System.out.println("地图销毁");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
