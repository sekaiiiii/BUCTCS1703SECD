package com.buct.museumguide.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CollectionResultMsg;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.CommentMsg;
import com.buct.museumguide.Service.CommentResultMsg;
import com.buct.museumguide.Service.EducationResultMsg;
import com.buct.museumguide.Service.ExhibitionResultMsg;
import com.buct.museumguide.Service.MuseumInfoMsg;
import com.buct.museumguide.Service.MuseumInfoResultMsg;
import com.buct.museumguide.Service.NewsResultMsg;
import com.buct.museumguide.Service.ResultMessage;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.Service.loginstatemessage;
import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.bean.Comment;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.bean.LoginState;

import com.buct.museumguide.bean.Museum;
import com.buct.museumguide.bean.Museum_Info_Full;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.util.RequestHelper;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
* 系统的默认页面，直接在这里构建页面0以及跳转逻辑，该页面的显示应按fragment实现
* 计划按逻辑实现为每个主要的方式（如地图/博物馆各种信息为一个具体的信息，其他的为fragment）
* */
public class HomeFragment extends Fragment {
    public static final String TAG ="HomeFragment" ;
    private HomeViewModel homeViewModel;
    private Banner homeBanner;
    private Museum showMuseum = null;
    private AlertDialog.Builder builder;
    private SharedPreferences Infos;
    private TextView museumName;
    private TextView introContent;
    private TextView visitContent;
    private TextView hometvComment;
    private TextView homeExhiScore;
    private TextView homeEnviScore;
    private TextView homeServScore;
    private RequestHelper requestHelper = new RequestHelper();
    private ArrayList<MuseumItem> bannerData = new ArrayList<>();
    private int curMuseumId = 3;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        bannerData.clear();
        Infos = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String info = Infos.getString("info", "");
        String museumId = Infos.getString("museumid_map", "");
        if (!info.equals("")) {
            Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onAttach: fromMap name"+ info);
            System.out.println(info);
        }
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        assert museumId != null;
        if(!museumId.equals("")) {
            curMuseumId = Integer.parseInt(museumId);
            editor.putInt("curMuseumId",curMuseumId).apply();
        }
        else
            editor.putInt("curMuseumId", 3).apply();
        Log.d(TAG, "onAttach: fromMap id = " + curMuseumId);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        homeBanner.start(); //开始轮播
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
//        EventBus.getDefault().unregister(this);
        homeBanner.stop(); //结束轮播
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // 这里可能有问题?
        homeViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        museumName = root.findViewById(R.id.museumList_button);
        introContent = root.findViewById(R.id.introContent);
        visitContent = root.findViewById(R.id.visitContent);
        hometvComment = root.findViewById(R.id.hometvComment);
        homeExhiScore = root.findViewById(R.id.homeExhiScore);
        homeEnviScore = root.findViewById(R.id.homeEnviScore);
        homeServScore = root.findViewById(R.id.homeServScore);

        Log.d(TAG, "onCreateView: getActivity" + getActivity());

        final SearchView homeSearch = root.findViewById(R.id.homeSearch);
        homeSearch.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_searchResult));
        final CardView cardViewIntro = root.findViewById(R.id.cardViewIntro);
        cardViewIntro.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_museumInfo));
        final CardView cardViewComment = root.findViewById(R.id.cardViewComment);
        cardViewComment.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_userComment));
        final Button homeMyComment=root.findViewById(R.id.homeMyComment);
        homeMyComment.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_myComment));
        museumName.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_museumList));

        homeBanner = root.findViewById(R.id.homeBanner);
        return root;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveMuseumInfo(MuseumInfoResultMsg museumInfoResultMsg) {
        String responseData = museumInfoResultMsg.res;
        Log.d(TAG, "onReceiveMuseumMsg: Museum = " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if (state.equals("1")) {
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("museum_list")));
                JSONObject resMuseum = jsonArray.getJSONObject(0);
                showMuseum = new Museum(resMuseum);
                homeViewModel.setMuseumLivaData(showMuseum);
                museumName.setText(showMuseum.getName().equals("")?"这里还没有内容":showMuseum.getName());
                introContent.setText(showMuseum.getIntroduction().equals("")?"这里还没有内容":showMuseum.getIntroduction());
                visitContent.setText((showMuseum.getTime() + showMuseum.getAttention()).equals("")?"这里还没有内容":showMuseum.getTime()+showMuseum.getAttention());
                String exhi_score = showMuseum.getExhibition_score().length() >= 4 ? showMuseum.getExhibition_score().substring(0, 4):showMuseum.getExhibition_score();
                String envi_score = showMuseum.getEnvironment_score().length() >= 4 ? showMuseum.getEnvironment_score().substring(0, 4):showMuseum.getEnvironment_score();
                String serv_score = showMuseum.getService_score().length() >= 4 ? showMuseum.getService_score().substring(0, 4):showMuseum.getService_score();
                homeExhiScore.setText(showMuseum.getExhibition_score().equals("")?"无":exhi_score);
                homeEnviScore.setText(showMuseum.getEnvironment_score().equals("")?"无":envi_score);
                homeServScore.setText(showMuseum.getService_score().equals("")?"无":serv_score);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putString("exhiScore",showMuseum.getExhibition_score()).apply();
                editor.putString("enviScore",showMuseum.getEnvironment_score()).apply();
                editor.putString("servScore",showMuseum.getService_score()).apply();
            } else {
                Log.d(HomeFragment.TAG, "museumInfor null");
            }
        } catch (JSONException e) {
            Log.e(HomeFragment.TAG, "onResponse: ", e);
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReceiveExhibition(ExhibitionResultMsg exhibitionResultMsg) {
        String responseData = exhibitionResultMsg.res;
        Log.d(TAG, "onReceiveExhibition: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if (state.equals("1")) {
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("exhibition_list")));
                if(jsonArray.length()==0) {
                    bannerData.add(new MuseumItem(1, null, null, null,null, null));
                }
                else {
                    Exhibition firstExhi = new Exhibition(jsonArray.getJSONObject(0));
                    bannerData.add(new MuseumItem(1, firstExhi, null, null, null, null));
                }
            } else {
                Log.d(HomeFragment.TAG, "Exhibition null");
                bannerData.add(new MuseumItem(1, null, null, null,null, null));
            }
            if(bannerData.size() == 4)
                reOrderBannerList();
        } catch (JSONException e) {
            Log.e(HomeFragment.TAG, "onResponse: ", e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReceiveCollection(CollectionResultMsg collectionResultMsg) {
        String responseData = collectionResultMsg.res;
        Log.d(TAG, "onReceiveCollection: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if (state.equals("1")) {
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("collection_list")));
                if(jsonArray.length()==0) {
                    bannerData.add(new MuseumItem(2, null, null,null,null,null));
                }
                else if(jsonArray.length()== 1){
                    Collection firstColl = new Collection(jsonArray.getJSONObject(0));
                    bannerData.add(new MuseumItem(2, null, firstColl, null,null,null));
                }
                else if(jsonArray.length() > 1) {
                    Collection firstColl = new Collection(jsonArray.getJSONObject(0));
                    Collection secondColl = new Collection(jsonArray.getJSONObject(1));
                    bannerData.add(new MuseumItem(2, null, firstColl, secondColl, null, null));
                }
            } else {
                Log.d(HomeFragment.TAG, "Collection null");
                bannerData.add(new MuseumItem(2, null, null,null,null,null));
            }
            if(bannerData.size() == 4)
                reOrderBannerList();
        } catch (JSONException e) {
            Log.e(HomeFragment.TAG, "onResponse: ", e);
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReceiveNews(NewsResultMsg newsResultMsg) {
        String responseData = newsResultMsg.res;
        Log.d(TAG, "onReceiveNews: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if (state.equals("1")) {
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data")));
                if(jsonArray.length()==0) {
                    bannerData.add(new MuseumItem(3, null, null,null,null,null));
                }
                else {
                    News firstNews = new News(jsonArray.getJSONObject(0));
                    bannerData.add(new MuseumItem(3, null, null, null,firstNews,null));
                }
            } else {
                Log.d(HomeFragment.TAG, "News null");
                bannerData.add(new MuseumItem(3, null, null,null,null,null));
            }
            if(bannerData.size() == 4)
                reOrderBannerList();
        } catch (JSONException e) {
            Log.e(HomeFragment.TAG, "onResponse: ", e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReceiveEducation(EducationResultMsg educationResultMsg) {
        String responseData = educationResultMsg.res;
        Log.d(TAG, "onReceiveEducation: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if (state.equals("1")) {
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("education_activity_list")));
                if(jsonArray.length()==0) {
                    bannerData.add(new MuseumItem(4, null, null,null,null,null));
                }
                else {
                    Education firstEdu = new Education(jsonArray.getJSONObject(0));
                    bannerData.add(new MuseumItem(4, null, null, null, null,firstEdu));
                }
            } else {
                Log.d(HomeFragment.TAG, "Education null");
                bannerData.add(new MuseumItem(4, null, null,null,null,null));
            }
            if(bannerData.size() == 4)
                reOrderBannerList();
        } catch (JSONException e) {
            Log.e(HomeFragment.TAG, "onResponse: ", e);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveComment(CommentResultMsg commentResultMsg) {
        String responseData = commentResultMsg.res;
        Log.d(TAG, "onReceiveComment: " + responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if (state.equals("1")) {
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("comment_list")));
                if(jsonArray.length() == 0 ) {
                    hometvComment.setText("这里还没有评价");
                    hometvComment.setGravity(Gravity.CENTER);
                    return;
                }
                Comment firstComment = new Comment(jsonArray.getJSONObject(0));
                if (!firstComment.getContent().equals(""))
                    hometvComment.setText(firstComment.getContent());
            } else {
                Log.d(HomeFragment.TAG, "null");
            }
        } catch (JSONException e) {
            Log.e(HomeFragment.TAG, "onResponse: ", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                    return true;
                }
                return false;
            }
        });
        Log.d(TAG, "onResume: ");
        bannerData.clear();
        requestHelper.getMuseumInfo(getActivity(), Objects.requireNonNull(Infos.getString("info", "中国地质博物馆")), -1);
        requestHelper.getExhibition(getActivity(), curMuseumId, "");
        requestHelper.getCollection(getActivity(), curMuseumId, "");
        requestHelper.getNews(getActivity(), curMuseumId, "", -1, -1);
        requestHelper.getEducation(getActivity(), curMuseumId, "");
        requestHelper.getComment(getActivity(), curMuseumId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "onDestroy: ");
    }

    @Subscribe
    public void GetResult(ResultMessage msg) throws IOException {
        File[] files=getActivity().getFilesDir().listFiles();
        for(File file:files){
            if(file.getName().equals("MuseumInfoCache")){
                System.out.println("已有缓存");return;
            }
        }
        Gson gson=new Gson();
        FileOutputStream outputStream;
        Museum_Info_Full info_full=gson.fromJson(msg.res,Museum_Info_Full.class);
        HashMap<Integer,Museum_Info_Full.museuminfo.realdata>data=new HashMap<>();
        for(int i=0;i<info_full.getData().getMuseum_list().size();i++){
            data.put(i+1,info_full.getData().getMuseum_list().get(i));
        }
        outputStream = getActivity().openFileOutput("MuseumInfoCache" , Context.MODE_PRIVATE);
        ObjectOutput out = new ObjectOutputStream(outputStream);
        out.writeObject(data);
        outputStream.close();
        System.out.println("文件写入完毕");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe(sticky = true)
    public void GetState(StateBroadCast msg) {
        if (msg.state == 1) {
            Log.d(TAG, "GetState: 收到了服务已启动的通知");
            bannerData.clear();
            requestHelper.getMuseumInfo(getActivity(), Objects.requireNonNull(Infos.getString("info", "中国地质博物馆")), -1);
            requestHelper.getExhibition(getActivity(), curMuseumId, "");
            requestHelper.getCollection(getActivity(), curMuseumId, "");
            requestHelper.getNews(getActivity(), curMuseumId, "", -1, -1);
            requestHelper.getEducation(getActivity(), curMuseumId, "");
            requestHelper.getComment(getActivity(), curMuseumId);
        }
    }

/*
* @ loginstatemessage 返回登录请求，按需决定是否跳转到登录页面
* */
    @Subscribe
    public void GetLoginState(loginstatemessage msg) {
        String res = msg.res;
        Gson gson = new Gson();
        LoginState state = gson.fromJson(res, LoginState.class);
        Boolean islogin = state.getData().getIs_login();
        System.out.println("登录状态" + state.getData().getIs_login());/**/
        if (!islogin) {
            Infos.edit().putString("user", "").apply();//首次启动
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请先登录");// 设置标题
            builder.setCancelable(false);
            // 为对话框设置取消按钮
            builder.setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_login);
                }
            });
            Looper.prepare();
            builder.create().show();// 使用show()方法显示对话框
            Looper.loop();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void reOrderBannerList() {
        Log.d(TAG, "reOrderBannerList: ");
        java.util.Collections.sort(bannerData, new Comparator<MuseumItem>() {
            @Override
            public int compare(MuseumItem o1, MuseumItem o2) {
                if(o1.viewType > o2.viewType)
                    return 1;
                if(o1.viewType == o2.viewType)
                    return 0;
                return -1;
            }
        });
        homeBanner.setAdapter(new HomeBannerAdapter(getContext(), bannerData))
                .setOnBannerListener((data, position) -> {
                    MuseumItem mData = (MuseumItem) data;
                    Bundle bundle = new Bundle();
                    bundle.putInt("showType", mData.viewType);
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_commonList, bundle);
                })
                .start();
    }
}