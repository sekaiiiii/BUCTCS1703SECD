package com.buct.museumguide.ui.FragmentForUsers.Upload;

import androidx.annotation.UiThread;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.ProgressDialog;
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
import androidx.navigation.Navigation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.buct.museumguide.bean.Museum_Info_Full;
import com.buct.museumguide.bean.get_collection_info;
import com.buct.museumguide.bean.get_exhibition_info;
import com.buct.museumguide.bean.selectType;
import com.buct.museumguide.bean.selectTyperes;
import com.buct.museumguide.util.CountingRequestBody;
import com.buct.museumguide.util.FileHelper;
import com.buct.museumguide.util.WebHelper;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class UploadAudio extends Fragment {

    private static final int REQUEST_CODE =102;
    private static final String TAG ="upload" ;
    private UploadAudioViewModel mViewModel;
    private Uri uri;//相对路径
    private String filepath;//绝对路径
    private int durtime;//音频时长
    private String itemid;//音频类型
    private String title;//音频标题
    private String describtion;//音频描述
    private TextView textView;//显示音乐文件
    private int select_type=-1;
    private Spinner spinner;//下拉选择
    private Button searchfile;//选择文件
    private HashMap<Integer,Integer>itemidget;
    ArrayList<String>items123;
    private Spinner betterSpinner;//博物馆选择器
    private final String[]items={"藏品讲解","博物馆讲解","展览讲解"};
    private final String[]nodata={"暂无数据"};
    private final String[]selectid={"collection_id","museum_id","exhibition_id"};
    private int museumid;
    private ProgressDialog dialog;
    private Button submit;
    private EditText settitle;
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x01){
                textView.setText("上传失败!");
            }
        }
    };
    private Spinner spinner2;
    public static UploadAudio newInstance() {
        return new UploadAudio();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.upload_audio_fragment, container, false);
        textView = root.findViewById(R.id.textView15);
        betterSpinner=root.findViewById(R.id.betterSpinner);//具体的
        spinner=root.findViewById(R.id.spinner3);//藏品、讲解、还是博物馆
        spinner2=root.findViewById(R.id.spinner2);//博物馆列表
        submit = root.findViewById(R.id.bt_submit_newfile);
        searchfile = root.findViewById(R.id.bt_upload_vioce);
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        Button myupload=root.findViewById(R.id.button12);//查看自己的上传
        myupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_uploadAudio_to_showUploadState);
            }
        });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"请先选择文件",Toast.LENGTH_SHORT).show();
            }
        });
        settitle=root.findViewById(R.id.editText);
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemidget=new HashMap<>();
        HashMap<Integer, Museum_Info_Full.museuminfo.realdata>map=FileHelper.GetHashMap(getActivity());
        ArrayList<String>museumname=new ArrayList<>();
        System.out.println("缓存的博物馆数量"+map.size());
        for(int i=1;i<=map.size();i++){
            museumname.add(map.get(i).getName());
        }
        ArrayAdapter<String>  adapterB= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, nodata);
        spinner2.setAdapter(adapterB);
        final ArrayAdapter<String> adapter1
                = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, museumname);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UploadAudio.this.itemid=selectid[position];
                UploadAudio.this.select_type=position;
                if(position==0){
                    spinner2.setVisibility(View.VISIBLE);
                    betterSpinner.setAdapter(adapter1);
                }else if(position==1){
                    spinner2.setVisibility(View.INVISIBLE);
                    betterSpinner.setAdapter(adapter1);
                }else{
                    spinner2.setVisibility(View.VISIBLE);
                    betterSpinner.setAdapter(adapter1);
                }
                Toast.makeText(getActivity(), UploadAudio.this.itemid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        betterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//选择博物馆
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                * 在这里要判断spinner的是什么，如果是博物馆则直接结束，否则进行网络请求
                * */
                UploadAudio.this.museumid=position+1;
                if(select_type==1){
                    Toast.makeText(getActivity(), String.valueOf(UploadAudio.this.museumid),Toast.LENGTH_SHORT).show();
                }else if(select_type==0){//藏品
                    EventBus.getDefault().post(new selectType(UploadAudio.this.museumid,0));
                }else{//展览
                    EventBus.getDefault().post(new selectType(UploadAudio.this.museumid,1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(itemidget.get(position));
                if(itemidget.get(position)!=null){
                    UploadAudio.this.museumid=itemidget.get(position);
                }else{
                    UploadAudio.this.museumid=-1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mViewModel = new ViewModelProvider(this).get(UploadAudioViewModel.class);
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
                    Log.d("上传的id号", "onClick: "+UploadAudio.this.itemid+ String.valueOf(UploadAudio.this.museumid));
                    if(UploadAudio.this.museumid==-1){
                        Toast.makeText(getActivity(),"您选择的是无效项目",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(UploadAudio.this.filepath.equals("")){
                        Toast.makeText(getActivity(),"您还没有选择文件",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        File file = new File(UploadAudio.this.filepath);
                        UploadAudio.this.title=settitle.getText().toString();
                        if(UploadAudio.this.title.equals("")){
                            Toast.makeText(getActivity(),"您还没有输入标题",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        UploadAudio.this.describtion="";
                        SharedPreferences Infos = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                        String cookie = Infos.getString("cookie", "");
                        System.out.println(UploadAudio.this.filepath+UploadAudio.this.title+UploadAudio.this.describtion+
                                UploadAudio.this.durtime+UploadAudio.this.itemid+UploadAudio.this.museumid+"cookie:" + cookie);
                        dialog =new ProgressDialog(getActivity());
                        dialog.setTitle("上传中");
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.setIndeterminate(false);
                        dialog.show();
                        OkHttpClient client = WebHelper.getInstance().client;
                        MediaType mediaType = MediaType.parse("application/json");
                        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                .setType(MediaType.parse("multipart/form-data"))
                                .addFormDataPart("music",file.getName(),
                                        RequestBody.create(MediaType.parse("audio/mpeg"),
                                                file))
                                .addFormDataPart("title", UploadAudio.this.title)
                                .addFormDataPart("duration", String.valueOf(UploadAudio.this.durtime))
                                .addFormDataPart(UploadAudio.this.itemid, String.valueOf(UploadAudio.this.museumid))
                                .build();
                        CountingRequestBody countingRequestBody = new CountingRequestBody(body, new CountingRequestBody.Listener() {
                            @Override
                            public void onRequestProgress(long byteWritted, long contentLength) {
                                //打印进度
                                if(byteWritted<=contentLength){

                                    DecimalFormat df = new DecimalFormat("0.00");
                                    if(getActivity()!=null){
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Log.d("pyh", "进度 ：" + (int)((1.0*byteWritted/contentLength)*100));
                                                dialog.setProgress((int)((1.0*byteWritted/contentLength)*100));
                                                textView.setText("已上传"+String.valueOf(df.format((1.0*byteWritted/contentLength)*100))+"%");
                                            }
                                        });
                                    }

                                }else{
                                    dialog.cancel();
                                }
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
                                String res=response.body().string();
                                System.out.println(res);
                                try {
                                    JSONObject jsonObject=new JSONObject(res);
                                    String status=jsonObject.get("status").toString();
                                    System.out.println(status);
                                    if(status.equals("1")){
                                        if(getActivity()!=null){
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    textView.setText("上传成功!");
                                                    dialog.cancel();
                                                }
                                            });
                                        }
                                    }else{
                                        Message message=new Message();
                                        message.what=0x01;
                                        handler.sendMessage(message);
                                        Looper.prepare();
                                        Toast.makeText(getActivity(),"上传失败",Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                        Looper.loop();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
        if(data==null)return;
        System.out.println(data.toString());
        this.uri=data.getData();
        this.filepath = FileHelper.getFilePathForN(this.uri, getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Subscribe
    public void ondataget(selectTyperes res){
        itemidget.clear();
        System.out.println("回调数据");
        if(res.type!=-1){
            Gson gson=new Gson();
            items123=new ArrayList<>();
            if(res.type==0){//藏品
                int count=0;
                get_collection_info infos=gson.fromJson(res.res,get_collection_info.class);
                for(int i=0;i<infos.getData().getCollection_list().size();i++){
                    System.out.println(infos.getData().getCollection_list().get(i).getName());
                    if(infos.getData().getCollection_list().get(i).getName()!=null){
                        items123.add(infos.getData().getCollection_list().get(i).getName());
                        itemidget.put(count++,
                                infos.getData().getCollection_list().get(i).getId());
                    }
                }
            }else{//展览
                int count=0;
                get_exhibition_info infos=gson.fromJson(res.res,get_exhibition_info.class);
                for(int i=0;i<infos.getData().getExhibition_list().size();i++){
                    System.out.println(infos.getData().getExhibition_list().get(i).getName());
                    if(infos.getData().getExhibition_list().get(i).getName()!=null){
                        items123.add(infos.getData().getExhibition_list().get(i).getName());
                        itemidget.put(count++,
                                infos.getData().getExhibition_list().get(i).getId());
                    }
                }
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String>  adapterA= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items123);
                    spinner2.setAdapter(adapterA);
                    spinner2.setClickable(true);
                }
            });
        }else{
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayAdapter<String>  adapterA= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, nodata);
                    spinner2.setAdapter(adapterA);
                    spinner2.setClickable(false);
                }
            });
        }
    }
}
