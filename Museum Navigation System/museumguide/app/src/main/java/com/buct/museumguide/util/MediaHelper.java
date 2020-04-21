package com.buct.museumguide.util;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

/*
* 用于对媒体服务的回调
* 参考教程
* 对每个list新建对象做到能播放能暂停就可以了
* */
public class MediaHelper extends MediaBrowserServiceCompat {
    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }
}
