package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MuseumInfoViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private final String TAG="MuseumInfoViewModel";
    private MutableLiveData<Bitmap>photo;
    public MuseumInfoViewModel(){
        photo=new MutableLiveData<>();
        String path="http://49.233.81.150/1.jpg";
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request = new Request.Builder()
                .url(path)
                .build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                byte[] Picture = response.body().bytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                photo.postValue(bitmap);
            }
        });
    }
    public LiveData<Bitmap>getphoto(){
        return photo;
    }
}
