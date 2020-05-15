package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.MuseumInfoResultMsg;
import com.buct.museumguide.Service.ResultMessage;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.util.RequestHelper;
import com.example.sidebar.WaveSideBarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DefaultFragment extends Fragment {

    public static final String TAG ="DefaultFragment" ;
    private com.buct.museumguide.bean.Museum showMuseum;
    private List<Museum> museumList = new ArrayList<>();
    private MuseumAdapter museumAdapter;
    private RequestHelper requestHelper = new RequestHelper();
    private SharedPreferences sharedPreferences;
    public static DefaultFragment newInstance() {return new DefaultFragment();}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //EventBus.getDefault().post(new CommandRequest("http://192.144.239.176:8080/api/android/get_museum_info"));
        View view = inflater.inflate(R.layout.museum_default_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.museumList_recyclerview0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        initMuseum();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        museumAdapter = new MuseumAdapter(museumList);
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

        WaveSideBarView waveSideBarView = (WaveSideBarView) view.findViewById(R.id.museum_default_sidebar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onReceive(MuseumInfoResultMsg museumInfoResultMsg) throws JSONException {
        String responseData = museumInfoResultMsg.res;
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
                    //System.out.println(showMuseum.getName());
                    temp_list.add(new Museum(R.drawable.ic_launcher_background,showMuseum.getName(),"国家一级博物馆","100"));
                }
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
        //museumList.add(new Museum(R.drawable.ic_launcher_background,"天台山大瀑布","国家二级博物馆","1000000"));
        for(int i=0; i<50; i++)
        {
            //museumList.add(new Museum("中国国家博物馆"));
            museumList.add(new Museum(R.drawable.ic_launcher_background,"故宫博物院","国家二级博物馆","1000000"));
        }
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
       // requestHelper.getMuseumInfo(getActivity(), Objects.requireNonNull(""),0);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        //EventBus.getDefault().post(new CommandRequest("http://192.144.239.176:8080/api/android/get_museum_info?order_by=0"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
