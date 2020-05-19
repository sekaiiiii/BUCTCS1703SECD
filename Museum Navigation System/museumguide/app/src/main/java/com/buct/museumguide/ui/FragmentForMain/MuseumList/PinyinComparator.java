package com.buct.museumguide.ui.FragmentForMain.MuseumList;

import java.util.Comparator;

//比较器类，根据Ascii码进行排序
public class PinyinComparator implements Comparator<Museum> {
    public int compare(Museum a1, Museum a2) {
        if(a1.getLetters().equals("@") || a2.getLetters().equals("#")){
            return -1;
        }
        else if(a1.getLetters().equals("#")|| a2.getLetters().equals("@")){
            return 1;
        }
        else {
            return a1.getLetters().compareTo(a2.getLetters());
        }
    }
}
