package com.buct.museumguide.ui.FragmentForMain.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.buct.museumguide.R;

public class SearchDownPageFragment extends Fragment {

    private SearchDownPageViewModel mViewModel;

    public static SearchDownPageFragment newInstance() {
        return new SearchDownPageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.search_down_page_fragment, container, false);

        final Button button1=view.findViewById(R.id.Button_hot1);
        final EditText editText=view.findViewById(R.id.search_editText);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(button1.getText());
            }
        });

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchDownPageViewModel.class);
        // TODO: Use the ViewModel
    }

}
