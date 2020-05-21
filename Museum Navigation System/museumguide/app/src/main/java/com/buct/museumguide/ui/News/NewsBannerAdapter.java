package com.buct.museumguide.ui.News;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.bean.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * 自定义新闻轮播图布局
 * 加载网络图片
 */
public class NewsBannerAdapter extends BannerAdapter<News, NewsBannerAdapter.NewsBannerContentHolder> {

    public NewsBannerAdapter(List<News> datas) {
        super(datas);
    }

    @Override
    public NewsBannerContentHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new NewsBannerContentHolder(BannerUtils.getView(parent, R.layout.news_banner_image));
    }

    @Override
    public void onBindView(NewsBannerContentHolder holder, News data, int position, int size) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
//        Glide.with(holder.itemView)
//                .load(data.getImgUrl())
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                .into(holder.bannerNewsImage);
        holder.bannerNewsTitle.setText(data.getTitle());
        holder.bannerNewsTime.setText(data.getTime().substring(0,10));
        holder.bannerNewsContent.setText(data.getContent());
    }
    static class NewsBannerContentHolder extends RecyclerView.ViewHolder {
//        ImageView bannerNewsImage;
        TextView bannerNewsTitle;
        TextView bannerNewsTime;
        TextView bannerNewsContent;

        NewsBannerContentHolder(@NonNull View view) {
            super(view);
//            this.bannerNewsImage = (ImageView) view.findViewById(R.id.bannerNewsImage);
            this.bannerNewsTitle = (TextView) view.findViewById(R.id.bannerNewsTitle);
            this.bannerNewsTime = (TextView) view.findViewById(R.id.bannerNewsTime);
            this.bannerNewsContent = (TextView) view.findViewById(R.id.bannerNewsContent);
        }
    }
}
