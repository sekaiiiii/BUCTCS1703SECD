package com.buct.museumguide.ui.FragmentForUsers.Regist;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.util.WebHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String>liveData1;
    private MutableLiveData<String>liveData2;

    public void  GetCode(String username,String mail_address, final Context activity, final View view){
        liveData1=new MutableLiveData<>();
        OkHttpClient okHttpClient1=WebHelper.getInstance().client;
        RequestBody body1 = new FormBody.Builder()
                .add("name",String.valueOf(username))
                .add("mail_address",String.valueOf(mail_address)).build();
            final Request request1=new Request.Builder()
                    .url("http://192.144.239.176:8080/api/android/want_register")
                    .post(body1).build();
            okHttpClient1.newCall(request1).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(Regist.TAG,"omFailure:",e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try{
                        String result1 = response.body().string();
                        JSONObject jsonobject3 =  new JSONObject(result1);
                        String state = jsonobject3.getString("status");//用于判断验证码是否发送成功
                        liveData1.postValue(state);
                    }catch (Exception e){
                        Log.e(Regist.TAG, "onResponse: ", e);
                    }
                }
            });
        }


    public void GetRegistState(String username,String mail_address,String code, String password, final Context activity, final View view){
        liveData2=new MutableLiveData<>();
        OkHttpClient okHttpClient2=WebHelper.getInstance().client;

        RequestBody body2 = new FormBody.Builder()
                .add("name",String.valueOf(username))
                .add("mail_address",String.valueOf(mail_address))
                .add("code",String.valueOf(code))
                .add("password",String.valueOf(password)).build();
            final Request request1=new Request.Builder()
                    .url("http://192.144.239.176:8080/api/android/register")
                    .post(body2).build();
            okHttpClient2.newCall(request1).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(Regist.TAG,"omFailure:",e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try{
                        String result2 = response.body().string();
                        JSONObject jsonobject4 =  new JSONObject(result2);
                        String state = jsonobject4.getString("status");//用于是否注册成功
                        liveData2.postValue(state);
                    }catch (Exception e){
                        Log.e(Regist.TAG, "onResponse: ", e);
                    }

                }
            });
        }


    public LiveData<String> getCode(String username, String mail_address, final Context activity, final View view){
        GetCode(username,mail_address,activity,view);
        return liveData1;
    }

    public LiveData<String> getState(String username, String mail_address,String code,String password,final Context activity, final View view){
        GetRegistState(username,mail_address,code,password,activity,view);
        return liveData2;
    }
}
