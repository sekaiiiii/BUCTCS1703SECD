package com.buct.museumguide.ui.FragmentForUsers.Regist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buct.museumguide.R;

public class Regist extends Fragment {

    public static final String TAG ="Regist" ;
    private RegistViewModel mViewModel;

    public static Regist newInstance() {
        return new Regist();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.regist_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(RegistViewModel.class);
        mViewModel =new ViewModelProvider(this).get(RegistViewModel.class);
        // TODO: Use the ViewModel

        final EditText username = getView().findViewById(R.id.text_username);
        final EditText email = getView().findViewById(R.id.text_email);
        final EditText password = getView().findViewById(R.id.text_password);
        final EditText definepsw = getView().findViewById(R.id.text_password_correction);
        final EditText code = getView().findViewById(R.id.text_code);
        Button bt_code =getView().findViewById(R.id.bt_gaincode);
        Button bt_define = getView().findViewById(R.id.button_register);


        //为获取验证码按钮添加点击事件
        bt_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String mail_address = email.getText().toString();
                String psws = password.getText().toString();
                String definepsws =definepsw.getText().toString();

                //传参给RegistViewModel处理
                if(name.length()>=2&&name.length()<=18) {
                    if(mail_address.equals(""))
                    {
                        Toast.makeText(getActivity(),"邮箱不能为空",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(password.length()>=6&&password.length()<=18){
                            if( psws.equals(definepsws)) {
                                mViewModel.getCode(name,mail_address,getActivity(),getView()).observe(getViewLifecycleOwner(), new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        if(s.equals("1")){
                                            Toast.makeText(getActivity(),"验证码发送成功",Toast.LENGTH_SHORT).show();
                                        }
                                        else if(s.equals("106")) {
                                            Toast.makeText(getActivity(), "验证码发送失败，用户名或邮箱已存在", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(s.equals("300")){
                                            Toast.makeText(getActivity(),"服务器发送邮件失败",Toast.LENGTH_SHORT).show();
                                        }
                                        else if(s.equals("101")){
                                            Toast.makeText(getActivity(),"验证码发送失败，邮箱格式错误",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getActivity(),"两次密码不同",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getActivity(),"密码长度应为6-18",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(),"用户名长度应为2-18",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //为确认按钮添加点击事件
        bt_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = username.getText().toString();
                String email_address = email.getText().toString();
                String codes= code.getText().toString();
                String psw = password.getText().toString();
                //将上述四个字符串作为参数传递给RegistViewModel处理
                if(codes.equals(""))
                {
                    Toast.makeText(getActivity(),"验证码不能为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    mViewModel.getState(names,email_address,codes,psw,getActivity(),getView()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if(s.equals("0")){
                                Toast.makeText(getActivity(),"注册失败,验证码错误或失效",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(getView()).navigate(R.id.action_regist_to_login);
                            }
                        }
                    });
                }
            }
        });
    }
}
