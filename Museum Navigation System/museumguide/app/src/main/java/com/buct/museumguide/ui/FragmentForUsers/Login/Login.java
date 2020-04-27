package com.buct.museumguide.ui.FragmentForUsers.Login;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.buct.museumguide.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Login extends Fragment {
    private static final String TAG ="Login" ;
    private LoginViewModel mViewModel;

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
        Button button_forget=getView().findViewById(R.id.button_forget_password);
        Button button_login=getView().findViewById(R.id.button_login);
        Button button_register=getView().findViewById(R.id.button_register);
        button_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient=new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                //Request request=new Request().
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("name",name.getText().toString());
                    jsonObject.put("password",password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonObject.length()>0){
                    String body=jsonObject.toString();
                    Request request=new Request.Builder()
                            .url("http://192.144.239.176:8080/api/android/login")
                            .post(RequestBody.create(body,mediaType)).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {


                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.e(TAG, "onFailure: ",e );
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            
                        }
                    });
                }
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_login_to_regist);
            }
        });
        // TODO: Use the ViewModel
    }

}
