package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ConmentFragment extends Fragment {

    private List<Museum> museumList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.museum_conment_layout, container, false);
        initMuseum();
        RecyclerView recyclerView = view.findViewById(R.id.museumList_recyclerview3);
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

    private void initMuseum(){
        for(int i=0; i<50; i++)
        {
            //museumList.add(new Museum("中国国家博物馆"));
            museumList.add(new Museum(R.drawable.ic_launcher_background,"中国科学技术馆","国家二级博物馆","1000000"));
        }
    }
}
