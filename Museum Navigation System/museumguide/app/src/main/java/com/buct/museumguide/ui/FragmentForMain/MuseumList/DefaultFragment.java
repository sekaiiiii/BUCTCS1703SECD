package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.util.PinyinUtil;
import com.buct.museumguide.util.RequestHelper;
import com.buct.museumguide.util.StreamUtils;
import com.example.sidebar.WaveSideBarView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class DefaultFragment extends Fragment {

    public static final String TAG ="DefaultFragment" ;
    private com.buct.museumguide.bean.Museum showMuseum;
    private List<Museum> museumList = new ArrayList<>();
    private List<com.buct.museumguide.bean.Museum> mMuseums = new ArrayList<>();
    private MuseumDefaultAdapter museumAdapter;
    private RequestHelper requestHelper = new RequestHelper();
    private SharedPreferences sharedPreferences;
    private PinyinComparator pinyinComparator;
    private WaveSideBarView waveSideBarView;
    private LinearLayoutManager manage;
    private RecyclerView recyclerView;
    private TitleItemDecoration titleItemDecoration;
    private MuseumListViewModel museumListViewModel;
    private View view;
    private List<Museum> cache;
    public static DefaultFragment newInstance() {return new DefaultFragment();}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        if(!EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().register(this);
//        }
//        requestHelper.getMuseumListDefault(getActivity(), Objects.requireNonNull(""),0);
//        System.out.println("onattach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //EventBus.getDefault().post(new CommandRequest("http://192.144.239.176:8080/api/android/get_museum_info"));
        view = inflater.inflate(R.layout.museum_default_layout, container, false);
        try {
            createFile();
            FileInputStream inputStream = Objects.requireNonNull(getActivity()).openFileInput("MuseumDefaultCache");
            System.out.println("文件内容"+inputStream.getChannel().size());
            if(inputStream.getChannel().size() != 0){
                ObjectInputStream in = new ObjectInputStream(inputStream);
                Museum[] obj = (Museum[]) in.readObject();
                List<Museum> cache = Arrays.asList(obj);
                museumList = cache;
                System.out.println("读取缓存数据");
            }
            else {
                museumListViewModel = new ViewModelProvider(this).get(MuseumListViewModel.class);
                try {
                    museumListViewModel.getMuseums(getContext()).observe(getViewLifecycleOwner(), new Observer<ArrayList<com.buct.museumguide.bean.Museum>>() {
                        @Override
                        public void onChanged(ArrayList<com.buct.museumguide.bean.Museum> museums) {
                            mMuseums = museums;
                            try {
                                museumList=filledData(mMuseums);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Collections.sort(museumList,pinyinComparator);
                            museumAdapter.updata(museumList);
                            try {
                                Cache(museumList);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (JSONException | IOException e){
                    e.printStackTrace();
                }
                System.out.println("第一次加载，存入缓存MuseumDefault");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        pinyinComparator = new PinyinComparator();
        waveSideBarView = (WaveSideBarView) view.findViewById(R.id.museum_default_sidebar);

        waveSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = museumAdapter.getPositionForSection(letter.charAt(0));
                if(position != -1) {
                    manage.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        recyclerView = view.findViewById(R.id.museumList_recyclerview0);
        manage = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manage);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        museumAdapter = new MuseumDefaultAdapter(museumList);
        museumAdapter.setOnItemClickListener(new MuseumDefaultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("已点击");
                //Toast.makeText(getContext(),"click: "+ position, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                System.out.println(museumAdapter.getTitle(position));
                editor.putString("info", museumAdapter.getTitle(position)).apply();
                int id = museumAdapter.getID(position);
                String x = ""+id;
                editor.putString("museumid_map",x).apply();
                editor.putString("Latitude",museumAdapter.getLatitude(position)).apply();
                editor.putString("Longtitude",museumAdapter.getLongtitude(position)).apply();
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getContext(),"click: "+ position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(museumAdapter);

        titleItemDecoration = new TitleItemDecoration(getActivity(),museumList);
        recyclerView.addItemDecoration(titleItemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        System.out.println("oncreateview");
        return view;
    }


    private List<Museum> filledData(List<com.buct.museumguide.bean.Museum> museums) throws JSONException {
        List<String> latitude = new ArrayList<>();
        List<String> longtitude = new ArrayList<>();
        List<Integer> Id = new ArrayList<>();
        List<String> imgUrl = new ArrayList<>();
        List<String> data = new ArrayList<>();
        for (int i=0; i<museums.size(); i++){
            JSONArray jsonArray = new JSONArray();
            jsonArray = museums.get(i).getImage_list();
            if(jsonArray.length()==0){
                imgUrl.add("");
            }
            else {
                String str = (String) jsonArray.get(0);
                str = "http://192.144.239.176:8080/"+str;
                imgUrl.add(str);
            }
            Id.add(museums.get(i).getId());
            data.add(museums.get(i).getName());
            latitude.add(museums.get(i).getLatitude());
            longtitude.add(museums.get(i).getLongitude());

        }
        List<Museum> mMuseumList = new ArrayList<>();

        for (int i=0 ; i<data.size(); i++){
            Museum museum = new Museum();
            museum.setName(data.get(i));
            museum.setImgUrl(imgUrl.get(i));
            museum.setId(Id.get(i));
            museum.setLatitude(latitude.get(i));
            museum.setLongtitude(longtitude.get(i));
            museum.setLevel("国家一级博物馆");
            //汉字转化为拼音
            String pinyin = PinyinUtil.getPingYin(data.get(i));
            String sortString = pinyin.substring(0,1).toUpperCase();

            //正则表达式，判断首字母是否英文字母
            if(sortString.matches("[A-Z]")){
                museum.setLetters(sortString.toUpperCase());
            }
            else {
                museum.setLetters("#");
            }
            mMuseumList.add(museum);
        }
        return mMuseumList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("onviewcreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onstart");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("onstop");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onactivitycreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onresume");
        //EventBus.getDefault().post(new CommandRequest("http://192.144.239.176:8080/api/android/get_museum_info?order_by=0"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("ondestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onpause");
    }

    public void Cache(List<Museum> museum) throws IOException {
        Museum[] obj = new Museum[museum.size()];
        museum.toArray(obj);
//        for (int i=0; i<museum.size();i++){
//            data.put(i+1,museum.get(i));
//            System.out.println("缓存数据" + museum.get(i).getName());
//        }
        FileOutputStream outputStream = Objects.requireNonNull(getActivity()).openFileOutput("MuseumDefaultCache",Context.MODE_PRIVATE);
        ObjectOutput out = new ObjectOutputStream(outputStream);
        out.writeObject(obj);
        outputStream.close();
        System.out.println("已缓存MuseumDefault");
    }
    public void createFile() throws IOException {
        File[] files=getActivity().getFilesDir().listFiles();
        for(File file:files){
            if(file.getName().equals("MuseumDefaultCache")){
                System.out.println("MuseumDefaultCache已有缓存");
                return;
            }
        }
        FileOutputStream outputStream = getActivity().openFileOutput("MuseumDefaultCache",Context.MODE_PRIVATE);
        outputStream.close();
    }
}
