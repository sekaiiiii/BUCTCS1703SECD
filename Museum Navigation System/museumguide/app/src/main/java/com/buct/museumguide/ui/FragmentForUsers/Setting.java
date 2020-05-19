package com.buct.museumguide.ui.FragmentForUsers;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import constant.UiType;
import listener.Md5CheckResultListener;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.UpdateMsg;
import com.buct.museumguide.Service.UpdateResult;
import com.buct.museumguide.bean.UpdateAppResult;
import com.buct.museumguide.util.DataCleanManager;
import com.buct.museumguide.util.FileHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String getcodeurl="http://192.144.239.176:8080/api/android/get_version_num";
    private final String apkurl="http://192.144.239.176:8080/file?fileName=release.apk";
    private String updateTitle = "发现新版本";
    private ProgressDialog dialog;
    private String updateContent = "1、Kotlin重构版\n2、支持自定义UI\n3、增加md5校验\n4、更多功能等你探索";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //
        TextView textView=getView().findViewById(R.id.textView23);
        TextView codeversion=getView().findViewById(R.id.textView11);
        codeversion.setText(String.valueOf(FileHelper.getAppVersionName(getActivity())));
        try {
            String size=DataCleanManager.getTotalCacheSize(getActivity());
            //System.out.println(size);
            textView.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button button=getView().findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(getActivity());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String size=DataCleanManager.getTotalCacheSize(getActivity());
                            //System.out.println(size);
                            textView.setText(size);
                            if(size.equals("0K")){
                                Toast.makeText(getActivity(),"清除成功！",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        Button button1=getView().findViewById(R.id.button8);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EventBus.getDefault().post(new UpdateMsg("1"));

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UpdateAppUtils.init(getActivity());
    }

    @Subscribe
    public void onUpdateResult(UpdateResult result) throws JSONException {
        System.out.println(result.msg);
        Gson gson=new Gson();
        UpdateAppResult result1=gson.fromJson(result.msg,UpdateAppResult.class);

        int version= (int) FileHelper.getAppVersionCode(getActivity());
        System.out.println("当前版本"+String.valueOf(version)+result1.getData().getVersion()+" "+
                result1.getData().getName()+" "+result1.getData().getDescription());
        if(version<Integer.valueOf(result1.getData().getVersion())){
            System.out.println("启动更新程序");
            UpdateConfig updateConfig = new UpdateConfig();
            updateConfig.setCheckWifi(true);
            updateConfig.setNeedCheckMd5(false);
            UiConfig uiConfig = new UiConfig();
            uiConfig.setUiType(UiType.PLENTIFUL);
            UpdateAppUtils
                    .getInstance()
                    .apkUrl(apkurl)
                    .updateTitle(updateTitle+ result1.getData().getName())
                    .updateContent(result1.getData().getDescription())
                    .uiConfig(uiConfig)
                    .updateConfig(updateConfig)
                    .setUpdateDownloadListener(new UpdateDownloadListener() {
                        @Override
                        public void onStart() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     dialog=new ProgressDialog(getActivity());
                                    dialog.setTitle("正在下载新版本中");
                                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                    dialog.setIndeterminate(false);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                }
                            });
                            System.out.println("开始下载更新");
                        }

                        @Override
                        public void onDownload(int progress) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.setProgress(progress);
                                }
                            });
                            System.out.println("下载"+String.valueOf(progress));
                        }

                        @Override
                        public void onFinish() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.cancel();
                                    Toast.makeText(getActivity(),"下载完毕",Toast.LENGTH_SHORT).show();
                                }
                            });
                            System.out.println("下载完毕");
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            System.out.println("下载失败");
                        }
                    })
                    .update();
        }else {
            Looper.prepare();
            Toast.makeText(getActivity(),"已经是最新版本",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
