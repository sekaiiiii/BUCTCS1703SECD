package com.buct.museumguide.ui.FragmentForMain.Search;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.StateBroadCast;
import com.buct.museumguide.ui.News.NewsRecyclerAdapter;
import com.buct.museumguide.util.RequestHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

public class SearchResult extends Fragment {

    private SearchResultViewModel mViewModel;
    private RequestHelper requestHelper;
    private String content;

    public static SearchResult newInstance() {
        return new SearchResult();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel =
                ViewModelProviders.of(this).get(SearchResultViewModel.class);
        View root = inflater.inflate(R.layout.search_result_fragment, container, false);


        //搜索
        ImageButton buttons=root.findViewById(R.id.button_search);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //返回
        Button cancel=root.findViewById(R.id.cancel_search);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String type;
        EditText editText=view.findViewById(R.id.search_editText);
        //下拉框
        Spinner spinner=view.findViewById(R.id.spinner_search_choice);


        //搜索
        ImageButton buttons=view.findViewById(R.id.button_search);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller=Navigation.findNavController(requireView());
                content=editText.getText().toString();
                String type;
                if(content.equals("")){
                    Toast.makeText(getContext(),"请输入内容再进行搜索",Toast.LENGTH_SHORT).show();
                }
                else{
                    Bundle bundle = new Bundle();
                    type=spinner.getSelectedItem().toString();
                    bundle.putString("type",type);
                    bundle.putString("content",content);
                    Navigation.findNavController(view).navigate(R.id.action_searchResult_to_searchResultShow,bundle);
                }

            }
        });

        //热搜
        Button hot1=view.findViewById(R.id.Button_hot1);
        hot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("国家博物馆");
            }
        });
        Button hot2=view.findViewById(R.id.Button_hot2);
        hot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("后母戊鼎");
            }
        });


        Button hot3=view.findViewById(R.id.Button_hot3);
        hot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("故宫博物院");
            }
        });

        Button hot4=view.findViewById(R.id.Button_hot4);
        hot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("黄玉夔龙纹扁壶");
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe(sticky = true)
    public void GetState(StateBroadCast msg) {
        if (msg.state == 1) {
//            System.out.println("收到了服务已启动的通知");
            Log.d("Hello", "GetState: " + "收到了服务已启动的通知");
            requestHelper.getMuseumInfo(getActivity(), content, -1);
            requestHelper.getExhibition(getActivity(), -1, content);
            requestHelper.getCollection(getActivity(), -1, content);
            requestHelper.getNews(getActivity(), -1, content);
            requestHelper.getEducation(getActivity(), -1, content);

        } else {
            EventBus.getDefault()
                    .post(new
                            CommandRequest
                            ("http://192.144.239.176:8080/api/android/get_museum_info"));
        }
    }


}
