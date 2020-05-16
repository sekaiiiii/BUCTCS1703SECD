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
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.MuseumInfoResultMsg;
import com.buct.museumguide.Service.ResultMessage;
import com.buct.museumguide.util.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TimeFragment extends Fragment {
    private List<Museum> museumList = new ArrayList<>();
    public static final String TAG ="TimeFragment" ;
    private com.buct.museumguide.bean.Museum showMuseum;
    private RequestHelper requestHelper = new RequestHelper();
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.museum_time_layout, container, false);
        //EventBus.getDefault().post(new CommandRequest("http://192.144.239.176:8080/api/android/get_museum_info"));
        //initMuseum();
        RecyclerView recyclerView = view.findViewById(R.id.museumList_recyclerview1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MuseumAdapter museumAdapter = new MuseumAdapter(museumList);
        museumAdapter.setOnItemClickListener(new MuseumAdapter.OnItemClickListener() {
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
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceive(MuseumInfoResultMsg museumInfoResultMsg) throws JSONException {
        String responseData = museumInfoResultMsg.res;
        Log.d("hello",responseData);
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String state = String.valueOf(jsonObject.get("status"));
            if(state.equals("1")){
                JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("museum_list")));
                ArrayList<Museum> temp_list = new ArrayList<>();
                for (int i = 0; i<jsonArray.length();i++)
                {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    showMuseum = new com.buct.museumguide.bean.Museum(object);
                    //System.out.println(showMuseum.getName());
                    temp_list.add(new Museum(R.drawable.ic_launcher_background,showMuseum.getName(),"国家一级博物馆","100"));
                }
                System.out.println(temp_list.size());
                museumList = temp_list;


            }
            else {
                Log.d(TAG, "null");
            }
        } catch (JSONException e){
            Log.e(TAG, "onResponse: ", e);
            e.printStackTrace();
        }
    }

    private void initMuseum(){
        for(int i=0; i<50; i++)
        {
            //museumList.add(new Museum("中国国家博物馆"));
            museumList.add(new Museum(R.drawable.ic_launcher_background,"中国国家博物馆","国家一级博物馆","1000000"));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("timefragment", "onResume: ");
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
        requestHelper.getMuseumInfo(getActivity(), Objects.requireNonNull(""),1);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("timefragment", "onPause: ");
        EventBus.getDefault().unregister(this);
    }
}
