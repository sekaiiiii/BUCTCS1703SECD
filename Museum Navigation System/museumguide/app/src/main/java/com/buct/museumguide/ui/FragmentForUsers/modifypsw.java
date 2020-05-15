package com.buct.museumguide.ui.FragmentForUsers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.ui.FragmentForUsers.modifypswViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Objects;

public class modifypsw extends Fragment {
    private modifypswViewModel mViewModel;
    public static final String TAG="Modify_password";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.modify_password, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =new ViewModelProvider(this).get(modifypswViewModel.class);
        final EditText psw_old=getView().findViewById(R.id.etext_oldps);
        final EditText psw_new=getView().findViewById(R.id.etext_newps);
        final EditText psw_correction=getView().findViewById(R.id.etext_defineps);
        Button button_ok=getView().findViewById(R.id.bt_define);
        Button button_back=getView().findViewById(R.id.bt_backup);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_psw=psw_new.getText().toString();
                String crr_psw=psw_correction.getText().toString();
                String old_psw=psw_old.getText().toString();
                if(old_psw.equals("") && new_psw.equals("") && crr_psw.equals(""))
                    Toast.makeText(getActivity(),"密码不能为空！",Toast.LENGTH_SHORT).show();
                else if(!new_psw.equals(crr_psw))
                    Toast.makeText(getActivity(),"新密码必须与确认密码相同",Toast.LENGTH_SHORT).show();
                else if(new_psw.equals(old_psw))
                    Toast.makeText(getActivity(),"新旧密码不能相同",Toast.LENGTH_SHORT).show();
                else if(new_psw.length()>=6&&new_psw.length()<=18){
                    mViewModel.getState(old_psw,new_psw,getActivity(),getView()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            System.out.println("观察到"+s);
                            if(s.equals("0")){
                                Toast.makeText(getActivity(),"密码修改失败",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(),"密码修改成功",Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(v).popBackStack();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(),"密码长度错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
