package com.buct.museumguide.ui.FragmentForUsers.Upload;

import androidx.annotation.UiThread;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.buct.museumguide.MainActivity;
import com.buct.museumguide.R;
import com.buct.museumguide.util.FileHelper;
import com.buct.museumguide.util.WebHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class UploadAudio extends Fragment {

    private static final int REQUEST_CODE =102;
    private static final String TAG ="upload" ;
    private UploadAudioViewModel mViewModel;
    public Uri uri;
    public String filepath;
    public static UploadAudio newInstance() {
        return new UploadAudio();
    }
    public void getdata(Uri uri){
        this.uri=uri;
    }
    public  String getFilepath(){
        return this.filepath;
    }
    private static String getFilePathForN(Uri uri, Context context) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getFilesDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.upload_audio_fragment, container, false);
        TextView textView = root.findViewById(R.id.resultget);  textView.setText("666");

        Button searchfile = root.findViewById(R.id.bt_upload_vioce);
            searchfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println();
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        Button submit = root.findViewById(R.id.bt_submit_newfile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"请先选择文件",Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UploadAudioViewModel.class);
        // TODO: Use the ViewModel
        }

    @Override
    public void onResume() {
        super.onResume();
        if (this.uri != null) {

            System.out.println(filepath);
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(filepath);
                mediaPlayer.prepare();
                System.out.println("time" + mediaPlayer.getDuration());
                ;
                mediaPlayer.release();
                // Cursor cursor = resolver.query(uri, new String[]{MediaStore.Audio.Media.EXTERNAL_CONTENT_URI}, null, null, null);
                // System.out.println(cursor.getColumnCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // ac=getActivity();
            Button submit = getView().findViewById(R.id.bt_submit_newfile);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(UploadAudio.this.uri);
                    try {
                        File file = new File(UploadAudio.this.filepath);
                        System.out.println(UploadAudio.this.filepath);
                        SharedPreferences Infos = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                        String cookie = Infos.getString("cookie", "");
                        System.out.println("cookie:" + cookie);
                        OkHttpClient client = WebHelper.getInstance().client;
                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .setType(MediaType.parse("multipart/form-data"))
                                .addFormDataPart("music",file.getName(),
                                        RequestBody.create(MediaType.parse("audio/mpeg"),
                                                file))
                                .addFormDataPart("title", "test")
                                .addFormDataPart("museum_id", "1")
                                .build();
                        Request request = new Request.Builder()
                                .url("http://192.144.239.176:8080/api/android/upload_explain")
                                .method("POST", body)
                                .addHeader("Content-Type", "multipart/form-data; boundary=<calculated when request is sent>")
                                .addHeader("Cookie", cookie)
                                .build();
                        client.newCall(request).enqueue(new Callback() {

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                System.out.println(response.body().string());
                            }

                            @Override
                            public void onFailure(Call arg0, IOException e) {
                                // TODO Auto-generated method stub
                                System.out.println(e.toString());

                            }

                        });
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       getdata(data.getData());
        this.filepath = getFilePathForN(this.uri, getActivity());
    }
}
