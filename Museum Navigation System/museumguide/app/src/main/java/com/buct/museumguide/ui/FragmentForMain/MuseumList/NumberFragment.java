package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;

import java.util.ArrayList;
import java.util.List;

public class NumberFragment extends Fragment {


    private List<Museum> museumList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.museum_num_layout, container, false);
        initMuseum();
        RecyclerView recyclerView = view.findViewById(R.id.museumList_recyclerview2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MuseumAdapter museumAdapter = new MuseumAdapter(museumList);
        recyclerView.setAdapter(museumAdapter);

        return view;
    }

    private void initMuseum(){
        for(int i=0; i<50; i++)
        {
            //museumList.add(new Museum("中国国家博物馆"));
            museumList.add(new Museum(R.drawable.ic_launcher_background,"天台山大瀑布","国家二级博物馆","1000000"));
        }
    }
}
