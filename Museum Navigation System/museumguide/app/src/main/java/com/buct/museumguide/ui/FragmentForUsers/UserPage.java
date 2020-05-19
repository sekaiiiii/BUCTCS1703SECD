package com.buct.museumguide.ui.FragmentForUsers;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.buct.museumguide.R;

public class UserPage extends Fragment {

    private UserPageViewModel mViewModel;

    public static UserPage newInstance() {
        return new UserPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.user_page_fragment, container, false);
        Button button1=root.findViewById(R.id.bt_modify_username);//修改用户名
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_userPage_to_modify_username);

            }
        });

        Button button3=root.findViewById(R.id.bt_modify_password);//修改密码
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_userPage_to_modifypsw);

            }
        });
        Button button_back=root.findViewById(R.id.bt_backup);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserPageViewModel.class);
        // TODO: Use the ViewModel
    }

}
