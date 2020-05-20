package com.buct.museumguide.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import com.buct.museumguide.bean.Audiolist;
import com.buct.museumguide.bean.music;
import com.buct.museumguide.ui.FragmentForUsers.Upload.audioitem;
import com.buct.museumguide.util.WebHelper;
import com.buct.museumguide.util.musicutil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media.MediaBrowserServiceCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MediaPlaybackService extends MediaBrowserServiceCompat implements MediaPlayer.OnPreparedListener
{
    final String playurl="http://192.144.239.176:8080/ZBAA-Nov-04-2019-0200Z.mp3-1588756996196.mp3";
    private static final String TAG=com.buct.museumguide.Service.MediaPlaybackService.class.getSimpleName() ;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    Audiolist list1;
    List<audioitem> list=new ArrayList<>();
    private MediaPlayer player=new MediaPlayer();
    private String cookie;
    private List<music>musicList;
    private String uri="http://192.144.239.176:8080/";
    private String metaid="";
    private long getAvailableActions() {
        long actions = PlaybackStateCompat.ACTION_PLAY
                | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID
                | PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH
                | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                | PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
        actions |= PlaybackStateCompat.ACTION_PAUSE;
        return actions;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player.setOnPreparedListener(this);
        musicList=new ArrayList<>();
        mediaSession=new MediaSessionCompat(this,"MediaPlaybackService");
        setSessionToken(mediaSession.getSessionToken());
        mediaSession.setCallback(Callback);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        SharedPreferences Infos=getSharedPreferences("data", Context.MODE_PRIVATE);
        cookie=Infos.getString("cookie","");
        Log.d(TAG, "onCreate: 服务启动");
        OkHttpClient client= WebHelper.getInstance().client;
        Request request=new Request.Builder()
                .url("http://192.144.239.176:8080/api/android/get_explain_info")
                .addHeader("Cookie",cookie)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("gg");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res=response.body().string();
                try {
                    JSONObject object=new JSONObject(res);
                    if(object.get("status").equals(0)){
                        System.out.println(res);
                    }else{
                        Gson gson=new Gson();
                        list1=gson.fromJson(res, Audiolist.class);
                        for(int i=0;i<list1.getDatas().getExplain_list().size();i++){
                            if(list1.getDatas().getExplain_list().get(i).getMuseum_id()!=null){
                                list.add(new audioitem(
                                        list1.getDatas().getExplain_list().get(i).getTitle(),
                                        list1.getDatas().getExplain_list().get(i).getFile(),
                                        list1.getDatas().getExplain_list().get(i).getName(),
                                        "M"+list1.getDatas().getExplain_list().get(i).getMuseum_id()));
                            }else if(list1.getDatas().getExplain_list().get(i).getCollection_id()!=null){
                                list.add(new audioitem(
                                        list1.getDatas().getExplain_list().get(i).getTitle(),
                                        list1.getDatas().getExplain_list().get(i).getFile(),
                                        list1.getDatas().getExplain_list().get(i).getName(),
                                        "C"+list1.getDatas().getExplain_list().get(i).getCollection_id()));
                            }else{
                                list.add(new audioitem(
                                        list1.getDatas().getExplain_list().get(i).getTitle(),
                                        list1.getDatas().getExplain_list().get(i).getFile(),
                                        list1.getDatas().getExplain_list().get(i).getName(),
                                        "E"+list1.getDatas().getExplain_list().get(i).getExhibition_id()));
                            }
                        }
                        for(int i=0;i<list.size();i++){
                            musicList.add(musicutil.SetMusic
                                    (String.valueOf(i+1),
                                            list.get(i).getTitle(),
                                            list.get(i).getAuthor(),
                                            list.get(i).getId(),
                                            uri+list.get(i).getFilename(),
                                            "无",
                                            "无",
                                           null));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(musicList.size());
            }
        });
    }
    private MediaSessionCompat.Callback Callback = new MediaSessionCompat.Callback() {
        private MediaMetadataCompat data;
        @Override
        public void onPlay() {
            super.onPlay();
            player.start();
        }

        @Override
        public void onPause() {
            super.onPause();
            player.pause();
        }

        @Override
        public void onPrepare() {
            super.onPrepare();
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);
            onPause();
            System.out.println("服务已收到播放申请");
            onPrepareFromMediaId(mediaId, extras);
        }

        @Override
        public void onPrepareFromMediaId(String mediaId, Bundle extras) {
            super.onPrepareFromMediaId(mediaId, extras);
            if(metaid.equals(mediaId)==false){
                System.out.println("选择了不同的歌曲");
                player.reset();metaid=mediaId;
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                int id=Integer.valueOf(mediaId);
                try {
                    System.out.println(musicList.get(id-1).getUrl());
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(musicList.get(id-1).getUrl());
                    player.prepareAsync();
                } catch (IOException e) {
                    System.out.println("无法播放");
                    e.printStackTrace();
                }
            }else{
                player.start();
            }

        }
    };

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot("root",null);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
       // System.out.println(parentId+" "+musicList.size());
        //System.out.println("线程"+Thread.currentThread().getId()+" "+getMainLooper().getThread().getId());
        List<MediaBrowserCompat.MediaItem>l=new ArrayList<>();
        result.detach();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicList.size()==0);
                if(musicList.size()>0){
                    for(int i=0;i<musicList.size();i++){
                        MediaDescriptionCompat des= new MediaDescriptionCompat.Builder()
                                .setMediaId(musicList.get(i).getMetaID())
                                .setTitle(musicList.get(i).getTitle())
                                .setSubtitle(musicList.get(i).getUrl())
                                .setDescription(musicList.get(i).getType())
                                .build();
                        MediaBrowserCompat.MediaItem m=new MediaBrowserCompat.MediaItem(des,MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);
                        l.add(m);
                    }
                    result.sendResult(l);
                }else{
                    result.sendResult(null);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        System.out.println("准备播放");
        mp.start();
    }
}
