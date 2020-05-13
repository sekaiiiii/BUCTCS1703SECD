package com.buct.museumguide.ui.FragmentForMain.CommonList;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.util.WebHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommonListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Exhibition>> exhibitionList;
    private MutableLiveData<ArrayList<Collection>> collectionList;
    private MutableLiveData<ArrayList<News>> newsList;
    private MutableLiveData<ArrayList<Education>> educationList;

    private void GET(final Context activity, String route, String type) {
        String url = activity.getResources().getString(R.string.baseUrl) + "/api/android/" + route;

        HttpUrl.Builder urlBuilder =HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("id", String.valueOf(3));

        OkHttpClient okHttpClient = WebHelper.getInstance().client;
        final Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(CommonList.TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")) {
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get(type)));
                        switch (type) {
                            case "exhibition_list":
                                ArrayList<Exhibition> tmp_exhi_list = new ArrayList<>();
                                for(int i=0; i < jsonArray.length(); ++i) {
                                    JSONObject tmp_obj = jsonArray.getJSONObject(i);
                                    tmp_exhi_list.add(new Exhibition(tmp_obj));
                                }
                                exhibitionList.postValue(tmp_exhi_list);
                                break;
                            case "collection_list":
                                ArrayList<Collection> tmp_coll_list = new ArrayList<>();
                                for(int i=0; i < jsonArray.length(); ++i) {
                                    JSONObject tmp_obj = jsonArray.getJSONObject(i);
                                    tmp_coll_list.add(new Collection(tmp_obj));
                                }
                                collectionList.postValue(tmp_coll_list);
                                break;
                            case "education_activity_list":
                                ArrayList<Education> tmp_edu_list = new ArrayList<>();
                                for(int i=0; i < jsonArray.length(); ++i) {
                                    JSONObject tmp_obj = jsonArray.getJSONObject(i);
                                    tmp_edu_list.add(new Education(tmp_obj));
                                }
                                educationList.postValue(tmp_edu_list);
                                break;
                        }
                    }
                    else {
                        Log.d(CommonList.TAG, "null");
                    }
                } catch (JSONException e) {
                    Log.e(CommonList.TAG, "onResponse: ", e);
                    e.printStackTrace();
                }
            }
        });
    }
    public void getAllData(final Context activity) {
        exhibitionList = new MutableLiveData<>();
        GET(activity, "get_exhibition_info", "exhibition_list");
        collectionList = new MutableLiveData<>();
        GET(activity, "get_collection_info", "collection_list");
        newsList = new MutableLiveData<>();
        educationList = new MutableLiveData<>();
        GET(activity,"get_education_activity_info", "education_activity_list");
    }
    public MutableLiveData<ArrayList<Exhibition>> getExhi(final Context activity, final View view) {
        return exhibitionList;
    }
    public MutableLiveData<ArrayList<Collection>> getColl(final Context activity, final View view) {
        return collectionList;
    }
    public MutableLiveData<ArrayList<Education>> getEdu(final Context activity, final View view) {
        return educationList;
    }
}
