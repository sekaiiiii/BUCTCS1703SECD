package com.buct.museumguide.ui.FragmentForMain.Search;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.buct.museumguide.R;

public class SearchResult extends Fragment {

    private SearchResultViewModel mViewModel;

    public static SearchResult newInstance() {
        return new SearchResult();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel =
                ViewModelProviders.of(this).get(SearchResultViewModel.class);
        View root = inflater.inflate(R.layout.search_result_fragment, container, false);


        replaceFragment(new SearchDownPageFragment());

        //搜索
        ImageButton buttons=root.findViewById(R.id.button_search);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AnotherDownPageFragment());
            }
        });

        //返回
        Button cancel=root.findViewById(R.id.cancel_search);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        //热搜
//        Button hot1=root.findViewById(R.id.Button_hot1);
//        hot1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment(new AnotherDownPageFragment());
//            }
//        });
//
//        Button hot2=root.findViewById(R.id.Button_hot2);
//        hot2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment(new AnotherDownPageFragment());
//            }
//        });
//
//
//        Button hot3=root.findViewById(R.id.Button_hot3);
//        hot3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment(new AnotherDownPageFragment());
//            }
//        });
//
//        Button hot4=root.findViewById(R.id.Button_hot4);
//        hot4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                replaceFragment(new AnotherDownPageFragment());
//            }
//        });


        return root;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getChildFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.downLayout,fragment);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);
        // TODO: Use the ViewModel
    }

}
