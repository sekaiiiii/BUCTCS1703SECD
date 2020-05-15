package com.buct.museumguide.ui.FragmentForUsers.Login;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Login extends Fragment {
    public static final String TAG ="Login" ;
    private LoginViewModel mViewModel;
    private SharedPreferences infos;
    public static Login newInstance() {
        return new Login();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =new ViewModelProvider(this).get(LoginViewModel.class);
        final EditText name=getView().findViewById(R.id.input_account_name);
        final EditText password=getView().findViewById(R.id.input_password);
        infos=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String islogin=infos.getString("user","");
       // Button button_forget=getView().findViewById(R.id.button_forget_password);
        Button button_login=getView().findViewById(R.id.button_login);
        Button button_register=getView().findViewById(R.id.button_register);
       /* button_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_login_to_modifypsw);
            }
        });*/
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString();
                String psw = password.getText().toString();
                mViewModel.getState(names,psw,getActivity(),getView()).observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        System.out.println("观察到"+s);
                        if(s.equals("0")){
                            Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_SHORT).show();
                        }else{
                            if(islogin.equals("")){
                                infos.edit().putString("user",names).apply();
                            }
                            Navigation.findNavController(getView()).navigate(R.id.action_login_to_navigation_notifications);
                        }
                    }
                });
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_login_to_regist);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        //getActivity().getSupportFragmentManager().popBackStackImmediate(Login.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
}
