package com.buct.museumguide.ui.News;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.MainActivity;
import com.buct.museumguide.R;
import com.buct.museumguide.ui.ClassForNews.WebViewer;

import java.util.ArrayList;

/*带参数的直接把新闻url链接拉到这里*/
public class DashboardFragment extends Fragment {
    private ArrayList<MuseumNews>newsList=new ArrayList<>();
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        //String name=getResources().getString(R.string.title_home)
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        /*final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        final Button button=root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), WebViewer.class);
                startActivity(intent);
            }
        });
        for(int i=0;i<50;i++){
            newsList.add(new MuseumNews(1,i + "溧阳看馆藏|元代梵文准提咒镜",
                    "地方焦点",
                    "2020-04-22 20:31:44",
                    "1",
                    "今天为大家带来溧阳馆藏第三十二期——元代梵文准提咒镜。铜镜直径8.2厘米,边厚0.3厘米。银锭形钮,主体纹饰为环绕镜钮两圈的梵文铭文圈,内圈为十六字梵...",
                    "https://baijiahao.baidu.com/s?id=1664675906753328795&wfr=spider&for=pc",
                    1,"http://08imgmini.eastday.com//mobile//20200427//20200427223051_212a5125226069466102e497168b127f_2_mwpm_03200403.jpg"));
        }

        RecyclerView recyclerView=root.findViewById(R.id.recyclerrView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        NewsAdapter adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
        adapter.addDatas(newsList);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.news_header_banner,recyclerView, false);
        adapter.setHeaderView(header);

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //MainActivity.url=
                Toast.makeText(getActivity(),newsList.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),WebViewer.class);
                intent.putExtra("uri",newsList.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return root;
    }

}
