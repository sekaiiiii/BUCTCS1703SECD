package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.media.MediaBrowserCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.AudioMessage;
import com.buct.museumguide.Service.PlayMessage;
import com.buct.museumguide.Service.StringMessage;
import com.buct.museumguide.bean.Audiolist;
import com.buct.museumguide.ui.FragmentForUsers.Upload.ShowUploadAdapter;
import com.buct.museumguide.ui.FragmentForUsers.Upload.audioitem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class Jianjie extends Fragment {

    private JianjieViewModel mViewModel;
    private RecyclerView recyclerView;
    public static Jianjie newInstance() {
        return new Jianjie();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.jianjie_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button play=getView().findViewById(R.id.button8);
        recyclerView=getView().findViewById(R.id.showaudio_jianjie);
        EventBus.getDefault().post(new StringMessage("0"));
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button stop=getView().findViewById(R.id.button10);
    }

    @Override
    public void onStart() {
        System.out.println("jianjieqidngle");
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(JianjieViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void getmsg(AudioMessage msg){
        AudioMessage s=msg;
        List<audioitem>l=new ArrayList<>();
        for(int i=0;i<s.list.size();i++){
            l.add(new audioitem(s.list.get(i).getDescription().getTitle().toString(),s.list.get(i).getDescription().getMediaId(),"导览"));
        }
        for(int i=0;i<s.list.size();i++){
            l.add(new audioitem(s.list.get(i).getDescription().getTitle().toString(),s.list.get(i).getDescription().getMediaId(),"导览"));
        }
        recyclerView=getView().findViewById(R.id.showaudio_jianjie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ShowUploadAdapter adapter=new ShowUploadAdapter(l);
        adapter.setClick(new ShowUploadAdapter.MyClick() {
            @Override
            public void click(View v) {
                EventBus.getDefault().post(new PlayMessage(String.valueOf(recyclerView.getChildAdapterPosition(v)+1)));
                Toast.makeText(getActivity(),String.valueOf(recyclerView.getChildAdapterPosition(v)),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        Toast.makeText(getActivity(),s.list.get(0).getDescription().getTitle(),Toast.LENGTH_SHORT).show();
    }
}
