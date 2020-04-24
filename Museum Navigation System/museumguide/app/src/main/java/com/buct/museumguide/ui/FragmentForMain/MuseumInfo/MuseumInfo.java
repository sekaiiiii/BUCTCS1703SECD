package com.buct.museumguide.ui.FragmentForMain.MuseumInfo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buct.museumguide.R;
import com.buct.museumguide.ui.map.MuseumMapInfo;

public class MuseumInfo extends Fragment {

    private MuseumInfoViewModel mViewModel;

    public static MuseumInfo newInstance() {
        return new MuseumInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= new ViewModelProvider(this).get(MuseumInfoViewModel.class);
        View root=inflater.inflate(R.layout.museum_info_fragment, container, false);
        final ImageView imageView=root.findViewById(R.id.imageView2);
        mViewModel.getphoto().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap s) {
                imageView.setImageBitmap(s);
            }
        });
        //Bitmap bitmap=getH
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MuseumInfoViewModel.class);
        // TODO: Use the ViewModel
    }

}
