package com.buct.museumguide.ui.FragmentForUsers.Upload;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.buct.museumguide.MainActivity;
import com.buct.museumguide.R;

public class UploadAudio extends Fragment {

    private static final int REQUEST_CODE =102;
    private static final String TAG ="upload" ;
    private UploadAudioViewModel mViewModel;
    private Uri uri;
    private String filepath;
    public static UploadAudio newInstance() {
        return new UploadAudio();
    }
    public void getdata(Uri uri){
        this.uri=uri;
    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            Log.i(TAG, "getRealPathFromUri: " + contentUri);
            String[] proj = { "_data" };
            cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            if (cursor != null && cursor.getColumnCount() > 0) {
                cursor.moveToFirst();
                int column_index = cursor.getColumnIndexOrThrow("_data");
                String path = cursor.getString(column_index);
                Log.i(TAG, "getRealPathFromUri: column_index=" + column_index + ", path=" + path);
                return path;
            } else {
                Log.w(TAG, "getRealPathFromUri: invalid cursor=" + cursor + ", contentUri=" + contentUri);
            }
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromUri failed: " + e  + ", contentUri=" + contentUri, e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        System.out.println(uri);

       // ac=getActivity();
        View root=inflater.inflate(R.layout.upload_audio_fragment, container, false);
        Button searchfile=root.findViewById(R.id.bt_upload_vioce);
        searchfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                getActivity().startActivityForResult(intent, REQUEST_CODE);
            }
        });
        Button submit=root.findViewById(R.id.bt_submit_newfile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UploadAudioViewModel.class);
        // TODO: Use the ViewModel
        /*if(this.uri!=null){
            ContentResolver resolver = getActivity().getContentResolver();
            Cursor cursor = resolver.query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            System.out.println(cursor.getColumnCount());
            if (cursor == null) {
                // 未查询到，说明为普通文件，可直接通过URI获取文件路径
                filepath = uri.getPath();
                return;
            }
            if (cursor.moveToFirst()) {
                // 多媒体文件，从数据库中获取文件的真实路径
                filepath = cursor.getString(cursor.getInt(0));

            }
            cursor.close();
            System.out.println(filepath);
        }*/
        System.out.println(getRealPathFromUri(getActivity(),this.uri));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* //这里执行你的代码
        //System.out.println("666");
        Uri uri = data.getData(); // 获取用户选择文件的URI
        // 通过ContentProvider查询文件路径
*/
       getdata(data.getData());
    }
}
