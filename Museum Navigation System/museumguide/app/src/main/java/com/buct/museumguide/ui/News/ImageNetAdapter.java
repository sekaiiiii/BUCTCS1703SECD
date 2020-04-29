package com.buct.museumguide.ui.News;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * 自定义布局，网络图片
 */
public class ImageNetAdapter extends BannerAdapter<MuseumNews, ImageHolder> {

    public ImageNetAdapter(List<MuseumNews> datas) {
        super(datas);
    }

    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
//        ImageView imageView = (ImageView) BannerUtils.getView(parent, R.layout.news_banner_image);
//        //通过裁剪实现圆角
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            BannerUtils.setBannerRound(imageView,20);
//        }
//        return new ImageHolder(imageView);
        return new ImageHolder(BannerUtils.getView(parent,R.layout.news_banner_image));
    }

    @Override
    public void onBindView(ImageHolder holder, MuseumNews data, int position, int size) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        Glide.with(holder.itemView)
                .load(data.getImgUrl())
//                .placeholder(R.drawable.loading)
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.bannerNewsImage);
        holder.bannerNewsTitle.setText(data.getTitle());
        holder.bannerNewsTime.setText(data.getTime().substring(0,10));
    }
}
