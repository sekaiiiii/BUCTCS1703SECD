package com.buct.museumguide.ui.FragmentForUsers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buct.museumguide.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link About#newInstance} factory method to
 * create an instance of this fragment.
 */
public class About extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public About() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment About.
     */
    // TODO: Rename and change types and number of parameters
    public static About newInstance(String param1, String param2) {
        About fragment = new About();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.1.6");
        Element desc = new Element();
        desc.setTitle("功能描述\n"+
        "此软件为计科1703软件工程的课设项目，可以浏览130个博物馆的信息新闻等\n" +
                "可以上传和播放官方和您上传的讲解\n" +
                "通过地图浏览您附近的博物馆\n"
                +"定制化的数据分析");
        return new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.drawable.ic_launcher2)
                .setDescription("博物馆导览和信息服务系统")
                .addItem(versionElement)
                .addItem(desc)
                .addGroup("欢迎访问我们的仓库")
                .addGitHub("https://github.com/Sekaiiiii/BUCTCS1703SECD/tree/group4","计科1703的软件工程仓库")
                .addGroup("如有任何问题，欢迎发送邮件反馈")
                .addEmail("916196773@qq.com", "Email")
                .create();
    }
}
