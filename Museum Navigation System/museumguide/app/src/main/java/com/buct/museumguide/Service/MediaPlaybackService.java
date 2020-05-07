package com.buct.museumguide.Service;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.IBinder;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.buct.museumguide.bean.music;
import com.buct.museumguide.util.musicutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

public class MediaPlaybackService extends MediaBrowserServiceCompat
{
    final String playurl="http://192.144.239.176:8080/ZBAA-Nov-04-2019-0200Z.mp3-1588756996196.mp3";
    private static final String TAG=com.buct.museumguide.Service.MediaPlaybackService.class.getSimpleName() ;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private MediaPlayer player=new MediaPlayer();
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
        Log.d(TAG, "onCreate: 服务启动");
        mediaSession=new MediaSessionCompat(this,"MediaPlaybackService");
        setSessionToken(mediaSession.getSessionToken());
        mediaSession.setCallback(Callback);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
    }
    private MediaSessionCompat.Callback Callback = new MediaSessionCompat.Callback() {
        private MediaMetadataCompat data;
        @Override
        public void onPlay() {
            super.onPlay();
            player.start();
            //mediaSession.setPlaybackState(PlaybackStateCompat.fromPlaybackState(stateBuilder));
        }

        @Override
        public void onPause() {
            super.onPause();
            player.pause();
        }

        @Override
        public void onPrepare() {
            super.onPrepare();
            music m= musicutil.SetMusic("1","讲解","bzd","导览",playurl,"不知道","xjbx", (long) 227);
            data=musicutil.SetData(m);
            mediaSession.setMetadata(data);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(data.getString(MediaMetadata.METADATA_KEY_MEDIA_URI).toString());
                        player.setDataSource(data.getString(MediaMetadata.METADATA_KEY_MEDIA_URI).toString());
                        player.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot("root",null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }


}
