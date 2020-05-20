package com.buct.museumguide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.buct.museumguide.Service.AudioMessage;
import com.buct.museumguide.Service.MediaPlaybackService;
import com.buct.museumguide.Service.OnOpenGetMessage;
import com.buct.museumguide.Service.PlayMessage;
import com.buct.museumguide.Service.StringMessage;
import com.buct.museumguide.ui.FragmentForUsers.Login.Login;
import com.buct.museumguide.ui.FragmentForUsers.Upload.UploadAudio;
import com.buct.museumguide.ui.home.HomeFragment;
import com.buct.museumguide.ui.map.MapGuide;
import com.buct.museumguide.util.WebHelper;
import com.buct.museumguide.util.dialogs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
/*主界面，貌似不需要动它，基本所有页面都在ui里，如果有看不懂的再问
*  除了能直接上模板的，剩下的全使用fragment了
* 记得在nav里注册
* */
public class MainActivity extends AppCompatActivity {
    private static final String TAG ="mainactivity" ;
    private MediaBrowserCompat mediaBrowser;
    private MediaControllerCompat mediaController;
    private  MediaSessionCompat.Token token;
    Intent intent1;
    public static void myToast(String s,Context context) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    /*其实有框架都不用绑定了2333*/
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           System.out.println("绑定获取数据服务");
           OnOpenGetMessage.binder binder= (OnOpenGetMessage.binder) service;
           binder.getcommand();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("解绑获取数据服务");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("主活动创建");
        super.onCreate(savedInstanceState);
        SharedPreferences Infos = getSharedPreferences("data", Context.MODE_PRIVATE);
        boolean isfirst=Infos.getBoolean("start",false);
        if(!isfirst){
            Intent intent2=new Intent(MainActivity.this,AppSlide.class);
            startActivity(intent2);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EventBus.getDefault().register(this);
        mediaBrowser=new MediaBrowserCompat(this,new ComponentName(this, MediaPlaybackService.class),callback,null);
        mediaBrowser.connect();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Intent intent=getIntent();
        String info=intent.getStringExtra("info");
        String museumId=intent.getStringExtra("museumid_map");

        Infos.edit().putString("info",info).apply();
        Infos.edit().putString("museumid_map",museumId).apply();
        AppBarConfiguration appBarConfiguration;
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
            BottomNavigationView bottomNavigationView=findViewById(R.id.nav_view);
            bottomNavigationView.setItemIconTintList(null);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId()==R.id.login||destination.getId()==R.id.regist){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bottomNavigationView.setVisibility(View.GONE);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    });
                }
                System.out.println(destination.getId()==R.id.login);
            }
        });
      //  System.out.println("huoqu"+Infos.getString("user",""));
        intent1=new Intent(MainActivity.this, OnOpenGetMessage.class);
        startService(intent1);
        bindService(intent1,connection,BIND_AUTO_CREATE);
    }
    private MediaBrowserCompat.ConnectionCallback callback
            = new MediaBrowserCompat.ConnectionCallback(){

        @Override
        public void onConnected() {
            super.onConnected();
            try {
                MediaSessionCompat.Token token = mediaBrowser.getSessionToken();
                mediaController = new MediaControllerCompat(getBaseContext(), token);
                mediaController.registerCallback(controlCallBack);
                mediaController.getTransportControls().prepare();
                System.out.println("success");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            //通过token，获取MediaController,第一个参数是context，第二个参数为token
        }

        @Override
        public void onConnectionFailed() {
            super.onConnectionFailed();
        }
    };
    private MediaControllerCompat.Callback controlCallBack = new MediaControllerCompat.Callback(){
        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
            MediaDescriptionCompat description=metadata.getDescription();
            String title = description.getTitle().toString();
            Log.d(TAG, "onMetadataChanged: "+title);
        }

        @Override
        public void onPlaybackStateChanged(final PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
        }
    };
    MediaBrowserCompat.SubscriptionCallback subcallback =
            new MediaBrowserCompat.SubscriptionCallback() {
                @Override
                public void onChildrenLoaded(@NonNull String parentId,
                                             @NonNull List<MediaBrowserCompat.MediaItem> children) {
                    //数据获取成功后的回调
                    try {
                        EventBus.getDefault().post(new AudioMessage(children));
                    }catch (Exception e){
                        System.out.println("post异常");
                        System.out.println(e);
                    }

                    System.out.println("回调成功"+children.size());
                }

                @Override
                public void onError(@NonNull String id) {
                    System.out.println("回调失败");
                    //数据获取失败的回调
                }
            };
    @Override
    public void onPause() {
        super.onPause();

        System.out.println("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaBrowser.disconnect();
        System.out.println("主活动停止");
        EventBus.getDefault().unregister(this);
        stopService(intent1);
        unbindService(connection);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getrefresh(StringMessage msg){
        System.out.println("服务已收到消息"+msg.msg);
        if(msg.msg.equals("0")){
            //刷新音频表，
            System.out.println("刷新订阅");
            mediaBrowser.unsubscribe("id");
            mediaBrowser.subscribe("id",subcallback);
        }else if(msg.msg.equals("666")){
            //广播订阅其他消息
            Toast.makeText(this,"收到了服务的通知",Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getplayrequest(PlayMessage msg){
        System.out.println("服务已收到播放"+msg.msg);
        if(msg.msg.equals("-1")){
            mediaController.getTransportControls().pause();
        }else {
            mediaController.getTransportControls().prepareFromMediaId(msg.msg,null);
        }
    }
/*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Navigation.findNavController(this,R.id.nav_host_fragment).navigateUp();
    }*/
}
