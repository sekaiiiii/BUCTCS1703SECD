package com.buct.museumguide.ui.FragmentForMain.Comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.buct.museumguide.R;

public class myComment extends Fragment {

    private MyCommentViewModel mViewModel;

    public static myComment newInstance() {
        return new myComment();
    }

    private RatingBar rating1,rating2,rating3;
    private TextView show1,show2,show3;
    public myComment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.my_comment_fragment, container, false);


        //返回
        ImageButton imageButton=(ImageButton) view.findViewById(R.id.back_commit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        //评星
        rating1=view.findViewById(R.id.Rating1);
        rating2=view.findViewById(R.id.Rating2);
        rating3=view.findViewById(R.id.Rating3);
        show1=view.findViewById(R.id.RatingShow1);
        show2=view.findViewById(R.id.RatingShow2);
        show3=view.findViewById(R.id.RatingShow3);

        rating1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="展览: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show1.setText(tmp);
            }
        });

        rating2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="环境: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show2.setText(tmp);
            }
        });

        rating3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="服务: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show3.setText(tmp);
            }
        });
        //评论框
        EditText edtMsg = (EditText)view.findViewById(R.id.submit_commit);
        edtMsg.setScrollbarFadingEnabled(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(MyCommentViewModel.class);
        // TODO: Use the ViewModel
    }

}
