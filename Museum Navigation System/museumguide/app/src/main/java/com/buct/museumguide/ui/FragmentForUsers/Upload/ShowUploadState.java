package com.buct.museumguide.ui.FragmentForUsers.Upload;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buct.museumguide.R;

import java.util.ArrayList;
import java.util.List;

public class ShowUploadState extends Fragment {

    private ShowUploadStateViewModel mViewModel;

    public static ShowUploadState newInstance() {
        return new ShowUploadState();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.show_upload_state_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ShowUploadStateViewModel.class);
        RecyclerView recyclerView=getView().findViewById(R.id.showAudios);
        List<audioitem> list=new ArrayList<>();
        for(int i=0;i<10;i++)
        list.add(new audioitem("震惊30亿人看到都疯了","233.avi","营销昊"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ShowUploadAdapter adapter=new ShowUploadAdapter(list);
        recyclerView.setAdapter(adapter);
        // TODO: Use the ViewModel
    }

}
