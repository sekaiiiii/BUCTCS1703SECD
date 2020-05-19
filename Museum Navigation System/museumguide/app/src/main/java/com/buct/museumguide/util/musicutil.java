package com.buct.museumguide.util;

import android.media.MediaMetadata;
import android.support.v4.media.MediaMetadataCompat;

import com.buct.museumguide.bean.music;

public class musicutil {
    static public MediaMetadataCompat SetData(music music){

        MediaMetadataCompat data=new MediaMetadataCompat.Builder()
                .putString(MediaMetadata.METADATA_KEY_MEDIA_ID,music.getMetaID())
                .putString(MediaMetadata.METADATA_KEY_TITLE, music.getTitle())
                .putString(MediaMetadata.METADATA_KEY_ARTIST,music.getAuthor())
                .putString(MediaMetadata.METADATA_KEY_ALBUM,music.getType())
                .putString(MediaMetadata.METADATA_KEY_MEDIA_URI,music.getUrl())
                .putString(MediaMetadata.METADATA_KEY_DISPLAY_DESCRIPTION,music.getDescribe())
                .putString(MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE,music.getSubtile())
                .putLong(MediaMetadata.METADATA_KEY_DURATION,music.getDuration()).build();
        return data;
    }
    static public music SetMusic(String MetaID, String title, String auther, String type, String url, String describe, String subtitle, Long duration){
        music music=new music();
        music.setAuthor(auther);
        music.setDescribe(describe);
        music.setDuration(duration);
        music.setMetaID(MetaID);
        music.setSubtile(subtitle);
        music.setTitle(title);
        music.setType(type);
        music.setUrl(url);
        return music;
    }
}
