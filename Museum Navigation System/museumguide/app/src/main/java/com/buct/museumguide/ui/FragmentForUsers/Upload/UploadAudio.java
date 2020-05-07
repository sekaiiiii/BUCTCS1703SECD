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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buct.museumguide.MainActivity;
import com.buct.museumguide.R;
import com.buct.museumguide.util.CountingRequestBody;
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
    private Uri uri;//相对路径
    private String filepath;//绝对路径
    private int durtime;//音频时长
    private int itemid;//音频类型
    private String title;//音频标题
    private String describtion;//音频描述
    private TextView textView;//显示音乐文件
    private Spinner spinner;//下拉选择
    private Button searchfile;//选择文件
    private Button submit;
    private EditText setdescribe;
    private EditText settitle;
    public static UploadAudio newInstance() {
        return new UploadAudio();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.upload_audio_fragment, container, false);
        textView = root.findViewById(R.id.textView15);
        spinner=root.findViewById(R.id.spinner3);
        final String[]items={"藏品讲解","博物馆讲解","展览讲解"};
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UploadAudio.this.itemid=position;
                //Toast.makeText(getActivity(),position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchfile = root.findViewById(R.id.bt_upload_vioce);
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
        submit = root.findViewById(R.id.bt_submit_newfile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"请先选择文件",Toast.LENGTH_SHORT).show();
            }
        });
        settitle=root.findViewById(R.id.editText);
        setdescribe=root.findViewById(R.id.editText4);
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UploadAudioViewModel.class);
        }

    @Override
    public void onResume() {
        super.onResume();
        if (this.uri != null) {
            System.out.println(filepath);
            textView.setText(filepath);
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(filepath);
                mediaPlayer.prepare();
                this.durtime=mediaPlayer.getDuration();
                System.out.println("time" + durtime);
                mediaPlayer.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
            submit = getView().findViewById(R.id.bt_submit_newfile);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        File file = new File(UploadAudio.this.filepath);
                        UploadAudio.this.title=settitle.getText().toString();
                        UploadAudio.this.describtion=setdescribe.getText().toString();
                        SharedPreferences Infos = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                        String cookie = Infos.getString("cookie", "");
                        System.out.println(UploadAudio.this.filepath+UploadAudio.this.title+UploadAudio.this.describtion+
                                UploadAudio.this.durtime+UploadAudio.this.itemid+"cookie:" + cookie);
                        /**/
                        OkHttpClient client = WebHelper.getInstance().client;
                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .setType(MediaType.parse("multipart/form-data"))
                                .addFormDataPart("music",file.getName(),
                                        RequestBody.create(MediaType.parse("audio/mpeg"),
                                                file))
                                .addFormDataPart("title", UploadAudio.this.title)
                                .addFormDataPart("duration", String.valueOf(UploadAudio.this.durtime))
                                .addFormDataPart("artist", UploadAudio.this.describtion)
                                .addFormDataPart("museum_id", String.valueOf(UploadAudio.this.itemid))
                                .build();
                        CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
                            @Override
                            public void onRequestProgress(long byteWritted, long contentLength) {
                                //打印进度
                                if(byteWritted<=contentLength)
                                Log.d("pyh", "进度 ：" + byteWritted + "/" + contentLength);
                            }
                        });
                        Request request = new Request.Builder()
                                .url("http://192.144.239.176:8080/api/android/upload_explain")
                                .method("POST", countingRequestBody)
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
/*
* 回调选择的文件名，并把文件名的相对地址转化为绝对地址
* */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.uri=data.getData();
        this.filepath = FileHelper.getFilePathForN(this.uri, getActivity());
    }
}
