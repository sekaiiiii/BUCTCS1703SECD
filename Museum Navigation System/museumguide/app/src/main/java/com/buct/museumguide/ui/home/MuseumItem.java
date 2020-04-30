package com.buct.museumguide.ui.home;

import java.util.ArrayList;
import java.util.List;

public class MuseumItem {
    public int viewType;
    private String type;
    private String title;
    private String content;
    private String time;
    private String imgUrl1;
    private String imgUrl2;
    private String colName1;
    private String colName2;
    public MuseumItem(int viewType, String type, String title, String content, String time, String imgUrl1, String imgUrl2, String colName1, String colName2) {
        this.viewType = viewType;
        this.type = type;
        this.title = title;
        this.content = content;
        this.time = time;
        this.imgUrl1 = imgUrl1;
        this.imgUrl2 = imgUrl2;
        this.colName1 = colName1;
        this.colName2 = colName2;
    }
    public String getType() { return type;}
    public String getTitle() { return title;}
    public String getContent() { return content;}
    public String getTime() { return time;}
    public String getImgUrl1() { return imgUrl1;}
    public String getImgUrl2() { return imgUrl2;}
    public String getColName1() { return colName1;}
    public String getColName2() { return colName2;}


    public static List<MuseumItem> getTestData() {
        List<MuseumItem> list = new ArrayList<>();
        list.add(new MuseumItem(1,"展览","妙相庄严——藏传佛教金铜造像艺术展","中国国家博物馆","2020-02-02","http://5b0988e595225.cdn.sohucs.com/images/20180102/1bf10505f9cd4580878411c44cc60ba0.jpeg", null, null, null));
        list.add(new MuseumItem(2,"馆藏精品","妙相庄严——藏传佛教金铜造像艺术展","中国国家博物馆","2020-02-02","http://img.m.focus.cn/q_70/app/48/4878fa2bfd5a1a93186c7aefd37b01e4.jpg", "http://img.mp.itc.cn/upload/20170322/56fee2eed94a493195e3ff98e79d5f50_th.jpeg", "名字1","名字2"));
        list.add(new MuseumItem(3,"馆内热闻","妙相庄严——藏传佛教金铜造像艺术展","中国国家博物馆","2020-02-02","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg", null, null, null));
        list.add(new MuseumItem(4,"教育活动","妙相庄严——藏传佛教金铜造像艺术展","中国国家博物馆","2020-02-02","https://img.zcool.cn/community/0148fc5e27a173a8012165184aad81.jpg", null, null,null));
        return list;
    }
}
