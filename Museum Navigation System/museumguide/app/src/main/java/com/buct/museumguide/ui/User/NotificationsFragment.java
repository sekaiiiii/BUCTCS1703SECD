package com.buct.museumguide.ui.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import com.buct.museumguide.R;
import com.buct.museumguide.util.WebHelper;

public class NotificationsFragment extends Fragment {
    private int state=-1;
    private NotificationsViewModel notificationsViewModel;
    private SharedPreferences Infos;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPreferences Infos = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        String cookie= WebHelper.getCookie(getActivity());
        if(cookie.length()==0)state=1;
        else state=0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     //   EventBus.getDefault().register(this);
        notificationsViewModel =
            new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        Infos=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        if(state==0){//已登录
            final TextView textView = root.findViewById(R.id.textView3);
            textView.setText(Infos.getString("user","游客"));
            final Button button0=root.findViewById(R.id.button3);//更改信息
            button0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_notifications_to_userPage);
                }
            });
            final Button button1=root.findViewById(R.id.button4);//自制讲解
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_notifications_to_uploadAudio);
                }
            });
            final Button button2=root.findViewById(R.id.button5);//意见反馈
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_notifications_to_feedBack);
                }
            });
            final Button button3=root.findViewById(R.id.button6);//退出账号
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cookie=Infos.getString("cookie","");
                    System.out.println(cookie);
                    notificationsViewModel.logout(cookie).observe(getViewLifecycleOwner(), new Observer<Integer>(){
                        @Override
                        public void onChanged(Integer integer) {
                            if(integer==1){
                                Infos.edit().putString("cookie","").apply();
                                Infos.edit().putString("user","").apply();
                                Navigation.findNavController(getView()).navigate(R.id.action_navigation_notifications_to_login);
                            }
                        }
                    });
                }
            });
            final Button button4=root.findViewById(R.id.button7);//更多设置
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_notifications_to_setting);
                }
            });
            final Button button5=root.findViewById(R.id.button8);//关于我们
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_notifications_to_about);
                }
            });
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (state == 1) {
            Navigation.findNavController(getView()).navigate(R.id.action_navigation_notifications_to_login);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
