package com.buct.museumguide.ui.FragmentForUsers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.util.WebHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FeedBack extends Fragment {
    /*
    * 多线程组件，异步执行
    * */
    private SharedPreferences infos;
    class uploadfeedback extends AsyncTask<String,Void,String>{
        public String res;
        String cookie;
        @Override
        protected String doInBackground(String... strings) {
            String t1=strings[0],t2=strings[1];
            System.out.println(cookie+t1+t2);
            if(cookie.length()==0)return "0";
            OkHttpClient client= WebHelper.getInstance().client;
            JSONObject object=new JSONObject();

            try {
                object.put("title",t1);object.put("content",t2);
            }catch (Exception e){
                System.out.println(e);
            }
            Request request= new Request.Builder()
                    .url(getActivity().getResources().getString(R.string.feedback_url))
                    .addHeader("Cookie",cookie)
                    .post(RequestBody.create(WebHelper.jsonmediaType,object.toString()))
                    .build();
            try {
                Response response=client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(uploadfeedback.this.res);
            return "0";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            infos=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            cookie=infos.getString("cookie","");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("dedao"+s);
            try {
                JSONObject object=new JSONObject(s);
                System.out.println(object.get("status"));
                if(object.get("status").equals(1)){
                    Toast.makeText(getActivity(),"感谢您的反馈",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"上传失败",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedBack() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedBack.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedBack newInstance(String param1, String param2) {
        FeedBack fragment = new FeedBack();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TextView t1=getView().findViewById(R.id.editText2);//问题名称
        final TextView t2=getView().findViewById(R.id.editText3);//改进措施
        final Button button=getView().findViewById(R.id.button);//提交
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qus=t1.getText().toString();
                String fd=t2.getText().toString();
                if(t1.length()==0||t2.length()==0){
                    Toast.makeText(getActivity(),"你还没有填写反馈！",Toast.LENGTH_SHORT).show();
                }else{
                    String[]s={qus,fd};
                    uploadfeedback uploadfeedback=new uploadfeedback();
                    uploadfeedback.execute(s);
                }
            }
        });
    }
}
