package com.buct.museumguide.ui.FragmentForMain.NewsList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.ui.ClassForNews.WebViewer;
import com.buct.museumguide.ui.News.ImageHolder;
import com.buct.museumguide.ui.News.ImageNetAdapter;
import com.buct.museumguide.ui.News.MuseumNews;
import com.buct.museumguide.ui.News.NewsAdapter;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;

public class NewsList extends Fragment {

    private NewsListViewModel mViewModel;
    private ArrayList<MuseumNews> newsList = new ArrayList<>();
    private Banner museumNewsBanner;

    public static NewsList newInstance() {
        return new NewsList();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        museumNewsBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        museumNewsBanner.stop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_list_fragment, container, false);
        RecyclerView museumNewsList = root.findViewById(R.id.museumNewsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        museumNewsList.setLayoutManager(layoutManager);
        museumNewsList.setItemAnimator(new DefaultItemAnimator());

        NewsAdapter adapter = new NewsAdapter();
        museumNewsList.setAdapter(adapter);
        initData();
        adapter.addDatas(newsList);

        // get and set header
        View header = LayoutInflater.from(getContext()).inflate(R.layout.museum_news_header_banner, museumNewsList, false);
        museumNewsBanner = (Banner) header.findViewById(R.id.museumNewsbanner);
        ImageButton museumNewsReturn = (ImageButton) header.findViewById(R.id.museumNewsReturn);
        museumNewsReturn.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());
        adapter.setHeaderView(header);

        museumNewsBanner.setAdapter(new ImageNetAdapter(MuseumNews.getTestData()))
                .setIndicator(new CircleIndicator(getContext()))
                .start();

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
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
        return root;
    }

    public void initData() {
        for(int i=0;i<20;i++){
            newsList.add(new MuseumNews(1,i + "溧阳看馆藏|元代梵文准提咒镜",
                    "地方焦点",
                    "2020-04-22 20:31:44",
                    "1",
                    "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                    "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                    1,"http://08imgmini.eastday.com//mobile//20200427//20200427223051_212a5125226069466102e497168b127f_2_mwpm_03200403.jpg"));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsListViewModel.class);
        // TODO: Use the ViewModel
    }

}
