package com.buct.museumguide.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * 自定义布局，多个不同UI切换
 */
public class HomeBannerAdapter extends BannerAdapter<MuseumItem, RecyclerView.ViewHolder> {
    private Context context;

    public HomeBannerAdapter(Context context, List<MuseumItem> datas) {
        super(datas);
        this.context = context;
    }

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
                exHolder.exhiType.setText(data.getType());
                exHolder.exhiTitle.setText(data.getTitle());
                exHolder.exhiContent.setText(data.getContent());
                //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
                Glide.with(exHolder.itemView)
                        .load(data.getImgUrl1())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(exHolder.exhiImg);
                break;
            case 2:
                HomeCollectionHolder coHolder = (HomeCollectionHolder) holder;
                coHolder.collType.setText(data.getType());
                coHolder.collName1.setText(data.getColName1());
                coHolder.collName2.setText(data.getColName2());
                Glide.with(coHolder.itemView)
                        .load(data.getImgUrl1())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(coHolder.collImg1);
                Glide.with(coHolder.itemView)
                        .load(data.getImgUrl2())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(coHolder.collImg2);
                break;
            case 3:
                HomeNewsHolder neHolder = (HomeNewsHolder) holder;
                neHolder.newsType.setText(data.getType());
                neHolder.newsTitle.setText(data.getTitle());
                neHolder.newsContent.setText(data.getContent());
                neHolder.newsTime.setText(data.getTime());
                Glide.with(neHolder.itemView)
                        .load(data.getImgUrl1())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(neHolder.newsImg);
                break;
            case 4:
                HomeEducationHolder edHolder = (HomeEducationHolder) holder;
                edHolder.eduType.setText(data.getType());
                edHolder.eduTitle.setText(data.getTitle());
                edHolder.eduContent.setText(data.getContent());
                //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
                Glide.with(edHolder.itemView)
                        .load(data.getImgUrl1())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(edHolder.eduImg);
                break;
        }
    }
}