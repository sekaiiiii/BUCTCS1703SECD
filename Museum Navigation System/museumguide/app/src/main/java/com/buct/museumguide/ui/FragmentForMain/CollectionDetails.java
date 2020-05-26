package com.buct.museumguide.ui.FragmentForMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.AudioMessage;
import com.buct.museumguide.Service.PlayMessage;
import com.buct.museumguide.Service.StringMessage;
import com.buct.museumguide.ui.FragmentForUsers.Upload.ShowUploadAdapter;
import com.buct.museumguide.ui.FragmentForUsers.Upload.audioitem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectionDetails extends Fragment {

    private CollectionDetailsViewModel collectionDetailsViewModel;
    private ImageView detail_coll_img;
    private TextView detail_coll_name;
    private TextView detail_coll_material_content;
    private TextView detail_coll_content;
    private RecyclerView recyclerView;
    private int currentpos=-1;
    private int count=0;
    private ShowUploadAdapter adapter;
    private int collid;
    private List<audioitem> l=new ArrayList<>();
    public static CollectionDetails newInstance() {
        return new CollectionDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().register(this);
        }
        collectionDetailsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CollectionDetailsViewModel.class);
        collectionDetailsViewModel.getCollLivaData().observe(getViewLifecycleOwner(),collection -> {
            collid=collection.getId();
        });
        View root = inflater.inflate(R.layout.collection_details_fragment, container, false);
        detail_coll_img = root.findViewById(R.id.detail_coll_img);
        detail_coll_name = root.findViewById(R.id.detail_coll_name);
        detail_coll_material_content = root.findViewById(R.id.detail_coll_material_content);
        detail_coll_content = root.findViewById(R.id.detail_coll_content);
        collectionDetailsViewModel.getCollLivaData().observe(getViewLifecycleOwner(), collection-> {
            if(!collection.getName().equals("")&&!collection.getName().equals("null"))
                detail_coll_name.setText(collection.getName());
            if(!collection.getMaterial().equals("")&&!collection.getMaterial().equals("null"))
                detail_coll_material_content.setText(collection.getMaterial());
            if(!collection.getContent().equals("")&&!collection.getContent().equals("null"))
                detail_coll_content.setText(collection.getContent());
            try {
                Glide.with(this)
                        .load(getImageUrl(collection.getImage_list()))
                        .apply(new RequestOptions().error(R.drawable.emptyphoto))
                        .into(detail_coll_img);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=getActivity().findViewById(R.id.recyclerviewcollect);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ShowUploadAdapter(l,getActivity());
        adapter.setClick(new ShowUploadAdapter.MyClick() {

            @Override
            public void click(View v) {
                count++;int pos=recyclerView.getChildAdapterPosition(v);
                if(currentpos==-1){
                    currentpos=pos;
                }
                if(currentpos!=pos&&currentpos!=-1){
                    //说明点击了其他按钮
                    Toast.makeText(getActivity(),"请先暂停当前播放再播放其他讲解",Toast.LENGTH_SHORT).show();
                    count--;
                    return;
                }
                if(count%2==1){
                    EventBus.getDefault().post(new PlayMessage(String.valueOf(recyclerView.getChildAdapterPosition(v)+1)));
                    Toast.makeText(getActivity(),"正在缓冲中，请稍后",Toast.LENGTH_SHORT).show();
                    adapter.isture.set(pos,false);
                }else{
                    currentpos=-1;count=0;
                    adapter.isture.set(pos,true);
                    EventBus.getDefault().post(new PlayMessage(String.valueOf(-1)));
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
    public String getImageUrl(JSONArray imgList) throws JSONException {
        String imgurl = "";
        if(imgList.length()==0){
            imgurl = "";
        }
        else {
            imgurl = "http://192.144.239.176:8080/" + imgList.get(0).toString();
        }
        return imgurl;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void getmsg(AudioMessage msg){
        AudioMessage s=msg;
        System.out.println("数据刷新");
        l.clear();
        System.out.println(String.valueOf(collid));
        for(int i=0;i<s.list.size();i++){
            String id=(String) s.list.get(i).getDescription().getDescription();
            String exrid=id.substring(1);
            if(id.charAt(0)=='C'&&Integer.valueOf(exrid).equals(collid))
                l.add(new audioitem(s.list.get(i).getDescription().getTitle().toString(),s.list.get(i).getDescription().getMediaId(),"导览", (String) s.list.get(i).getDescription().getDescription()));
        }
        if(l.size()==0){
            Toast.makeText(getActivity(),"此藏品暂无讲解",Toast.LENGTH_LONG).show();
        }
        System.out.println("list"+l.size());
        System.out.println(recyclerView);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(count%2==1){
            EventBus.getDefault().post(new PlayMessage(String.valueOf(-1)));
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new StringMessage("0"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
