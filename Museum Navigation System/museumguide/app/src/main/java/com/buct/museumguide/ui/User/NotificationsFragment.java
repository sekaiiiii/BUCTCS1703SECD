package com.buct.museumguide.ui.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import com.buct.museumguide.R;
import com.buct.museumguide.ui.FragmentForUsers.SettingsActivity;

public class NotificationsFragment extends Fragment {
    private int state=-1;
    private NotificationsViewModel notificationsViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPreferences Infos = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        String cookie=Infos.getString("cookie","");
        if(cookie.length()==0)state=1;
        else state=0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SharedPreferences preferences=getSharedPreferences
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        if(state==0){//已登录
            final TextView textView = root.findViewById(R.id.textView3);
            final ImageView imageView=root.findViewById(R.id.imageView);
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
                    SharedPreferences Infos=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                    Infos.edit().putString("cookie","").apply();
                    Toast.makeText(getActivity(),"假装退出登录了",Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_notifications_to_login);
                }
            });
            final Button button4=root.findViewById(R.id.button7);//更多设置
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), SettingsActivity.class));
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
        /*
        * 获取焦点重写onresume监听按钮
        * */
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK){
                    //Toast.makeText(getActivity(), "按了返回键", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}
