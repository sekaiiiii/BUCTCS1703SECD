package com.buct.museumguide.Service;

import android.support.v4.media.MediaBrowserCompat;

import java.util.List;

public class AudioMessage {
    public final List<MediaBrowserCompat.MediaItem> list;
    public AudioMessage(List<MediaBrowserCompat.MediaItem> list) {
        this.list = list;
    }
}
