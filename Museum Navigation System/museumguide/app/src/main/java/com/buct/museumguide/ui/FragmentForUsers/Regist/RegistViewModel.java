package com.buct.museumguide.ui.FragmentForUsers.Regist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.util.WebHelper;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String>liveData1;
    private MutableLiveData<String>liveData2;
    //存放cookie
    private List<String> cookies;
    private String cookie="";

    public void  GetCode(String username,String mail_address, final Context activity, final View view){
        liveData1=new MutableLiveData<>();
        OkHttpClient okHttpClient1=WebHelper.getInstance().client;
        RequestBody body1 = new FormBody.Builder()
                .add("name",String.valueOf(username))
                .add("mail_address",String.valueOf(mail_address)).build();
        final Request request1=new Request.Builder()
                .url(activity.getResources().getString(R.string.verification_code_url))
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
                    Headers header = response.headers();
                    cookies = header.values("Set-Cookie");
                    JSONObject jsonobject3 =  new JSONObject(result1);

                    String state = jsonobject3.getString("status");//用于判断验证码是否发送成功
                    System.out.println(state);
                    if(state.equals("1")){ System.out.println("验证码");
                        String session=header.values("Set-Cookie").get(0);
                        String sessionID = session.substring(0, session.indexOf(";"));
                        System.out.println(sessionID);
                        cookie=sessionID;
                        liveData1.postValue(state);
                    }
                    else{
                        String error = jsonobject3.getString("error_code");
                        liveData1.postValue(error);
                        System.out.println("null");
                    }
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
                .url(activity.getResources().getString(R.string.want_regrist_url))
                .addHeader("Cookie",cookie)
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
                   /* Looper.prepare();
                    Toast.makeText(activity,result2,Toast.LENGTH_SHORT).show();
                    Looper.loop();*/
                    JSONObject jsonobject4 =  new JSONObject(result2);
                    String state = jsonobject4.getString("status");//用于是否注册成功
                    if(state.equals("1")){
                        System.out.println("注册成功");
                        liveData2.postValue("1");
                    }
                    else{
                        System.out.println("null");
                        liveData2.postValue("0");
                    }
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
