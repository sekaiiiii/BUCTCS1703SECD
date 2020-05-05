package com.buct.museumguide.ui.FragmentForMain.Comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.buct.museumguide.R;

public class UserComment extends Fragment {

    private UserCommentViewModel mViewModel;

    public static UserComment newInstance() {
        return new UserComment();
    }

    String data[]={"用户名                                                 2020.4.20\nXXXXXXXXX\nXXXXXXX",
            "用户名                                                 2020.4.20\nXXXXXXXXX\nXXXXXXX",
            "用户名                                                 2020.4.20\nXXXXXXXXX\nXXXXXXX",
            "用户名                                                 2020.4.20\nXXXXXXXXX\nXXXXXXX",
            "用户名                                                 2020.4.20\nXXXXXXXXX\nXXXXXXX",
            "用户名                                                 2020.4.20\nXXXXXXXXX\nXXXXXXX"};

    private RatingBar rating4,rating5,rating6;
    private TextView show4,show5,show6;
    public UserComment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_user_commit, container, false);

        //我要评论
        Button commit=root.findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_userComment_to_myComment);
            }
        });

        //返回
        ImageButton imageButton=root.findViewById(R.id.backHome);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        //评星

        rating4=root.findViewById(R.id.Rating4);
        rating5=root.findViewById(R.id.Rating5);
        rating6=root.findViewById(R.id.Rating6);
        show4=root.findViewById(R.id.RatingShow4);
        show5=root.findViewById(R.id.RatingShow5);
        show6=root.findViewById(R.id.ratingShow6);

        rating4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="展览 :  ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show4.setText(tmp);
            }
        });

        rating5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="环境 :  ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show5.setText(tmp);
            }
        });

        rating6.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="服务 :  ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show6.setText(tmp);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView=(ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,data));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserCommentViewModel.class);
        // TODO: Use the ViewModel
    }

}
