package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.MuseumInfoResultMsg;
import com.buct.museumguide.Service.MuseumListCommentResultMsg;
import com.buct.museumguide.util.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentFragment extends Fragment {

    private List<Museum> museumList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private RequestHelper requestHelper = new RequestHelper();
    private com.buct.museumguide.bean.Museum showMuseum;
    private MuseumCommentAdapter museumAdapter;
    private String TAG = "CommentFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.museum_conment_layout, container, false);
        //initMuseum();
        RecyclerView recyclerView = view.findViewById(R.id.museumList_recyclerview3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        museumAdapter = new MuseumCommentAdapter(museumList);
        museumAdapter.setOnItemClickListener(new MuseumCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("已点击");
                //Toast.makeText(getContext(),"click: "+ position, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                System.out.println(museumAdapter.getTitle(position));
                editor.putString("info", museumAdapter.getTitle(position)).apply();
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"click: "+ position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(museumAdapter);

        return view;
    }


    @Subscribe(sticky = true)
    public void onReceive(MuseumListCommentResultMsg museumListCommentResultMsg) throws JSONException {
        String responseData = museumListCommentResultMsg.res;
        Log.d("hello",responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if(state.equals("1")){
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("museum_list")));
                System.out.println(jsonArray);
                ArrayList<Museum> temp_list = new ArrayList<>();
                for (int i = 0; i<jsonArray.length();i++)
                {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    showMuseum = new com.buct.museumguide.bean.Museum(object);
                    JSONArray imgList = showMuseum.getImage_list();
                    String imgurl = "";
                    if(imgList.length()==0){
                        imgurl = "";
                    }
                    else {
                        imgurl = showMuseum.getImage_list().get(0).toString();
                        imgurl = "http://192.144.239.176:8080/"+imgurl;
                    }
                    //System.out.println(showMuseum.getName());
                    String exhibition =showMuseum.getExhibition_score();
                    String environment =showMuseum.getEnvironment_score();
                    String service = showMuseum.getService_score();
                    DecimalFormat df = new DecimalFormat("0.0");
                    if(exhibition == "null"){
                        exhibition = "展览暂无用户评价";
                    }
                    else {
                        double value = Double.valueOf(exhibition.toString());
                        exhibition = df.format(value);
                        exhibition = "展览："+exhibition;
                    }
                    if(environment == "null"){
                        environment = "环境暂无用户评价";
                    }
                    else {
                        double value = Double.valueOf(environment.toString());
                        environment = df.format(value);
                        environment = "环境："+ environment;
                    }
                    if(service =="null"){
                        service ="服务暂无用户评价";

                    }
                    else {
                        double value = Double.valueOf(service.toString());
                        service = df.format(value);
                        service= "服务：" + service;
                    }

                    temp_list.add(new Museum(imgurl,showMuseum.getName(),"国家一级博物馆",exhibition,environment,service));
                }
                museumList.clear();
                museumList.addAll(temp_list);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        museumAdapter.notifyDataSetChanged();
                    }
                });


            }
            else {
                Log.d(TAG, "null");
            }
        } catch (JSONException e){
            Log.e(TAG, "onResponse: ", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestHelper.getMuseumListComment(getActivity(), Objects.requireNonNull(""),3);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
