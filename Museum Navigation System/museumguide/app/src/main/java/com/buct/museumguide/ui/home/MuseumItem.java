package com.buct.museumguide.ui.home;

import com.buct.museumguide.bean.Collection;
import com.buct.museumguide.bean.Education;
import com.buct.museumguide.bean.Exhibition;
import com.buct.museumguide.bean.Museum;
import com.buct.museumguide.bean.News;
import com.buct.museumguide.ui.FragmentForMain.MuseumList.MuseumList;

import java.util.ArrayList;
import java.util.List;

public class MuseumItem {
    public int viewType;
    private Exhibition exhibition;
    private Collection collection1;
    private Collection collection2;
    private News news;
    private Education education;

    public MuseumItem(int viewType, Exhibition exhibition, Collection collection1, Collection collection2, News news, Education education) {
        this.viewType = viewType;
        this.exhibition = exhibition;
        this.collection1 = collection1;
        this.collection2 = collection2;
        this.news = news;
        this.education = education;
    }
    public Exhibition getExhibition() {
        return exhibition;
    }
    public Collection getCollection1() {
        return collection1;
    }
    public Collection getCollection2() {
        return collection2;
    }
    public News getNews() {
        return news;
    }

    public Education getEducation() {
        return education;
    }
    public static ArrayList<MuseumItem> getTestData() {
        ArrayList<MuseumItem> list = new ArrayList<>();
        list.add(new MuseumItem(1, Exhibition.getTestData(),null, null, null, null));
        list.add(new MuseumItem(2, null, Collection.getTestData(), Collection.getTestData(), null,null));
        list.add(new MuseumItem(3, null, null, null, News.getOneTestData(), null));
        list.add(new MuseumItem(4, null, null, null,null, Education.getTestData()));
        return list;
    }
}
