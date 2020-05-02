package com.buct.museumguide.ui.FragmentForMain.CommonList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.ui.ClassForNews.WebViewer;
import com.buct.museumguide.ui.News.NewsRecyclerAdapter;

import java.util.ArrayList;

public class CommonList extends Fragment {

    private CommonListViewModel mViewModel;
    private ArrayList<News> newsList = new ArrayList<>();
    private ArrayList<Education> eduList = new ArrayList<>();

    public static CommonList newInstance() {
        return new CommonList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        assert bundle != null;
        int showType = bundle.getInt("showType");
        Toast.makeText(getActivity(), "" + showType ,Toast.LENGTH_SHORT).show();

        View root = inflater.inflate(R.layout.common_list_fragment, container, false);
        ImageButton topNavReturn = (ImageButton) root.findViewById(R.id.topNavReturn);
        topNavReturn.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());
        TextView topNavTitle = root.findViewById(R.id.topNavTitle);

        RecyclerView commonList = root.findViewById(R.id.commonList);
        LinearLayoutManager linearLayoutManager;
        StaggeredGridLayoutManager gridLayoutManager;
        if(showType == 2) {
            gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            commonList.setLayoutManager(gridLayoutManager);
        }
        else {
            linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            commonList.setLayoutManager(linearLayoutManager);
        }
        commonList.setItemAnimator(new DefaultItemAnimator());

        switch (showType) {
            case 1:
                topNavTitle.setText("展览");
                ExhiRecyclerAdapter exhiAdapter = new ExhiRecyclerAdapter();
                commonList.setAdapter(exhiAdapter);
                exhiAdapter.addDatas(Exhibition.getTestData());
                exhiAdapter.setOnItemClickListener(new ExhiRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Navigation.findNavController(getView()).navigate(R.id.action_commonList_to_exhibitionDetails);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
            case 2:
                topNavTitle.setText("馆藏精品");
                CollRecyclerAdapter collAdapter = new CollRecyclerAdapter();
                commonList.setAdapter(collAdapter);
                collAdapter.addDatas(Collection.getTestData());
                collAdapter.setOnItemClickListener(new CollRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Navigation.findNavController(getView()).navigate(R.id.action_commonList_to_collectionDetails);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
            case 3:
                topNavTitle.setText("馆内热闻");
                NewsRecyclerAdapter newsAdapter = new NewsRecyclerAdapter();
                commonList.setAdapter(newsAdapter);
                newsList = News.getTestData();
                newsAdapter.addDatas(newsList);
                newsAdapter.setOnItemClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //MainActivity.url=
                        Toast.makeText(getActivity(),newsList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), WebViewer.class);
                        intent.putExtra("uri",newsList.get(position).getUrl());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
            default:
                topNavTitle.setText("教育活动");
                EduRecyclerAdapter eduAdapter = new EduRecyclerAdapter();
                commonList.setAdapter(eduAdapter);
                eduAdapter.addDatas(Education.getTestData());
                eduAdapter.setOnItemClickListener(new EduRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Navigation.findNavController(getView()).navigate(R.id.action_commonList_to_educationDetails);
                    }
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                break;
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CommonListViewModel.class);
        // TODO: Use the ViewModel
    }

    public void initData() {
        for(int i=0;i<20;i++){
            newsList.add(new News(1,i + "溧阳看馆藏|元代梵文准提咒镜",
                    "地方焦点",
                    "2020-04-22 20:31:44",
                    "1",
                    "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                    "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                    1,"http://08imgmini.eastday.com//mobile//20200427//20200427223051_212a5125226069466102e497168b127f_2_mwpm_03200403.jpg"));
        }
    }
}
