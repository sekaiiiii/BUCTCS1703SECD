package com.buct.museumguide.ui.FragmentForUsers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

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
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class modifypswViewModel extends ViewModel {
    private MutableLiveData<String> liveData;
    public void GetModifypswState(String psw_old, String psw_new, final Context activity, final View view){
        liveData=new MutableLiveData<>();
        OkHttpClient okHttpClient= WebHelper.getInstance().client;//这里最好是写死的，将情况按参数匹配
        MediaType mediaType = MediaType.parse("application/json");
        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("old_password",psw_old);
            jsonObject.put("new_password",psw_new);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonObject.length()>0){
            String body=jsonObject.toString();
            String cookie=WebHelper.getCookie(activity);
            final Request request=new Request.Builder()
                    .url(activity.getResources().getString(R.string.set_password_url))
                    .addHeader("cookie",cookie)
                    .post(RequestBody.create(body,mediaType))
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(com.buct.museumguide.ui.FragmentForUsers.modifypsw.TAG, "onFailure: ",e );
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    try {
                        String res=response.body().string();
                        Headers header=response.headers();
                        List<String> cookies = header.values("Set-Cookie");
                        JSONObject jsonObject1=new JSONObject(res);
                        String state=String.valueOf(jsonObject1.get("status"));//根据status判断是否可用
                        if(state.equals("1")){
                            System.out.println("success");
                        }
                        else{
                            String error_code=jsonObject1.getString("error_code");
                            String error_des=jsonObject1.getString("error_des");
                            System.out.println(error_code+error_des);
                        }
                        liveData.postValue(state);
                    }catch (Exception e){
                        Log.e(com.buct.museumguide.ui.FragmentForUsers.modifypsw.TAG, "onResponse: ", e);
                    }
                }
            });
        }
    }
    public LiveData<String> getState(String psw_old, String psw_new, final Context activity, final View view){
        GetModifypswState(psw_old,psw_new,activity,view);
        return liveData;
    }
}
