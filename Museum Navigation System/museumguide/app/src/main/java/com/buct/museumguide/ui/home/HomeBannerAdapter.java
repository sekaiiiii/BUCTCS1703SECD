package com.buct.museumguide.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.bean.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义布局，多个不同UI切换
 */
public class HomeBannerAdapter extends BannerAdapter<MuseumItem, RecyclerView.ViewHolder> {
    private Context context;

    public HomeBannerAdapter(Context context, ArrayList<MuseumItem> datas) {
        super(datas);
        this.context = context;
    }

    /*public void addDatas(List<MuseumItem> datas) {
        news.addAll(datas);
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemViewType(int position) {
        return getData(getRealPosition(position)).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new HomeExhibitionHolder(context, BannerUtils.getView(parent, R.layout.home_banner_exhibition));
            case 2:
                return new HomeCollectionHolder(context, BannerUtils.getView(parent, R.layout.home_banner_collection));
            case 3:
                return new HomeNewsHolder(context, BannerUtils.getView(parent, R.layout.home_banner_news));
            case 4:
                return new HomeEducationHolder(context, BannerUtils.getView(parent, R.layout.home_banner_education));
//            default:
        }
        return new HomeExhibitionHolder(context, BannerUtils.getView(parent, R.layout.home_banner_exhibition));
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, MuseumItem data, int position, int size) {
        int viewType = data.viewType;
        switch (viewType) {
            case 1:
                HomeExhibitionHolder exHolder = (HomeExhibitionHolder) holder;
                exHolder.exhiType.setText("展览");
                Exhibition exhibition = data.getExhibition();
                if(exhibition != null) {
                    exHolder.exhiTitle.setText(exhibition.getName());
                    exHolder.exhiContent.setText(exhibition.getContent());
                    //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
                    Glide.with(exHolder.itemView)
                            .load(exhibition.getImgUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(exHolder.exhiImg);
                }
                else {
                    Log.d("HomeBanner", "onBindView: 展览无数据");
                    exHolder.noExhi.setVisibility(View.VISIBLE);
                    exHolder.exhiTitle.setVisibility(View.INVISIBLE);
                    exHolder.exhiContent.setVisibility(View.INVISIBLE);
                    exHolder.exhiImg.setVisibility(View.INVISIBLE);
                }
                break;
            case 2:
                HomeCollectionHolder coHolder = (HomeCollectionHolder) holder;
                coHolder.collType.setText("馆藏精品");
                Collection collection1 = data.getCollection1();
                Collection collection2 = data.getCollection2();
                if(collection1 != null && collection2 != null) {
                    coHolder.collName1.setText(collection1.getName());
                    coHolder.collName2.setText(collection2.getName());
                    Glide.with(coHolder.itemView)
                            .load(collection1.getImgUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(coHolder.collImg1);
                    Glide.with(coHolder.itemView)
                            .load(collection2.getImgUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(coHolder.collImg2);
                }
                else {
                    Log.d("HomeBanner", "onBindView: 藏品无数据");
                    coHolder.noColl.setVisibility(View.VISIBLE);
                    coHolder.coll1Wrap.setVisibility(View.INVISIBLE);
                    coHolder.coll2Wrap.setVisibility(View.INVISIBLE);
                }
                break;
            case 3:
                HomeNewsHolder neHolder = (HomeNewsHolder) holder;
                neHolder.newsType.setText("馆内热闻");
                News news = data.getNews();
                if(news != null) {
                    neHolder.newsTitle.setText(news.getTitle());
                    neHolder.newsContent.setText(news.getContent());
                    neHolder.newsTime.setText(news.getTime());
                    Glide.with(neHolder.itemView)
                            .load(news.getImgUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(neHolder.newsImg);
                }
                else {
                    Log.d("HomeBanner", "onBindView: 新闻无数据");
                    neHolder.noNews.setVisibility(View.VISIBLE);
                    neHolder.newsTitle.setVisibility(View.INVISIBLE);
                    neHolder.newsContent.setVisibility(View.INVISIBLE);
                    neHolder.newsTime.setVisibility(View.INVISIBLE);
                    neHolder.newsImg.setVisibility(View.INVISIBLE);
                }
                break;
            case 4:
                HomeEducationHolder edHolder = (HomeEducationHolder) holder;
                edHolder.eduType.setText("教育活动");
                Education education = data.getEducation();
                if(education != null) {
                    edHolder.eduTitle.setText(education.getName());
                    edHolder.eduContent.setText(education.getContent());
                    //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
                    Glide.with(edHolder.itemView)
                            .load(education.getImgUrl())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(edHolder.eduImg);
                }
                else {
                    Log.d("HomeBanner", "onBindView: 展览无数据");
                    edHolder.noEdu.setVisibility(View.VISIBLE);
                    edHolder.eduTitle.setVisibility(View.INVISIBLE);
                    edHolder.eduContent.setVisibility(View.INVISIBLE);
                    edHolder.eduImg.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}