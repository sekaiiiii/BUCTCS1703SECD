package com.buct.museumguide.ui.FragmentForUsers.Upload;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Audiolist;
import com.buct.museumguide.util.WebHelper;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowUploadState extends Fragment {
    String cookie;
    private ShowUploadStateViewModel mViewModel;
    Audiolist list1;
    List<audioitem> list;
    RecyclerView recyclerView;
    ShowUploadAdapter_showonly adapter;
    public static ShowUploadState newInstance() {
        return new ShowUploadState();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       /* Intent serviceIntend = new Intent(getActivity(), GetUploadService.class);
        getActivity().startService(serviceIntend);*/
       View root=inflater.inflate(R.layout.show_upload_state_fragment, container, false);
        SharedPreferences Infos=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        cookie=Infos.getString("cookie","");
         recyclerView=root.findViewById(R.id.showAudios);
        list=new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
         adapter=new ShowUploadAdapter_showonly(list,getActivity());
        recyclerView.setAdapter(adapter);
        OkHttpClient client= WebHelper.getInstance().client;
        Request request=new Request.Builder()
                .url("http://192.144.239.176:8080/api/android/get_myself_explain?ppn=100")
                .addHeader("Cookie",cookie)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("gg");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res=response.body().string();
                try {
                    JSONObject object=new JSONObject(res);
                    if(object.get("status").equals(0)){
                        System.out.println(res);
                    }else{
                        Gson gson=new Gson();
                        list1=gson.fromJson(res, Audiolist.class);
                        for(int i=0;i<list1.getDatas().getExplain_list().size();i++){
                            if(list1.getDatas().getExplain_list().get(i).getMuseum_id()!=null){
                                list.add(new audioitem(
                                        list1.getDatas().getExplain_list().get(i).getTitle(),
                                        list1.getDatas().getExplain_list().get(i).getFile(),
                                        list1.getDatas().getExplain_list().get(i).getName(),
                                        "M"+list1.getDatas().getExplain_list().get(i).getMuseum_id()));
                            }else if(list1.getDatas().getExplain_list().get(i).getCollection_id()!=null){
                                list.add(new audioitem(
                                        list1.getDatas().getExplain_list().get(i).getTitle(),
                                        list1.getDatas().getExplain_list().get(i).getFile(),
                                        list1.getDatas().getExplain_list().get(i).getName(),
                                        "C"+list1.getDatas().getExplain_list().get(i).getCollection_id()));
                            }else{
                                list.add(new audioitem(
                                        list1.getDatas().getExplain_list().get(i).getTitle(),
                                        list1.getDatas().getExplain_list().get(i).getFile(),
                                        list1.getDatas().getExplain_list().get(i).getName(),
                                        "E"+list1.getDatas().getExplain_list().get(i).getExhibition_id()));
                            }
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ShowUploadStateViewModel.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
