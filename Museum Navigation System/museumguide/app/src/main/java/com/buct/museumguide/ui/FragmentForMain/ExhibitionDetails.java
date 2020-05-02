package com.buct.museumguide.ui.FragmentForMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.buct.museumguide.R;

public class ExhibitionDetails extends Fragment {

    private ExhibitionDetailsViewModel mViewModel;

    public static ExhibitionDetails newInstance() {
        return new ExhibitionDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exhibition_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExhibitionDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

}
