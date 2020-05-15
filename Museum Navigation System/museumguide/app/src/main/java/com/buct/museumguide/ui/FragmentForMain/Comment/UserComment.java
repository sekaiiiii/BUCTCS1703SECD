package com.buct.museumguide.ui.FragmentForMain.Comment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buct.museumguide.R;
import com.buct.museumguide.Service.CommandRequest;
import com.buct.museumguide.Service.ResultMessage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.LinkedList;
import java.util.List;

public class UserComment extends Fragment {

    private UserCommentViewModel mViewModel;

    public static UserComment newInstance() {
        return new UserComment();
    }

    private List<PerComment> CommentList =new LinkedList<>();

    private RatingBar rating4,rating5,rating6;
    private TextView show4,show5,show6;
    public UserComment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.user_comment_fragment, container, false);

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
                String tmp="展览: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show4.setText(tmp);
            }
        });

        rating5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="环境: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show5.setText(tmp);
            }
        });

        rating6.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String tmp="服务: ";String tmp2=String.valueOf(v);
                tmp=tmp+tmp2+"  ";
                show6.setText(tmp);
            }
        });

        RecyclerView recyclerView=root.findViewById(R.id.comment_recyclerview_commit);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter commentAdapter=new CommentAdapter(CommentList);
        recyclerView.setAdapter(commentAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault()
                .post(new
                        CommandRequest
                        ("http://192.144.239.176:8080/api/android/get_museum_comment?id=1"));
//        mViewModel = ViewModelProviders.of(this).get(UserCommentViewModel.class);
        // TODO: Use the ViewModel
    }

    @Subscribe
    public void onRecieve(ResultMessage resultMessage){
//            Log.d("Hello",resultMessage.res);
            Gson gson=new Gson();
            String responseData = resultMessage.res;
            JsonObject jsonObject = new JsonParser().parse(responseData).getAsJsonObject();
            JsonObject subJsonObject =jsonObject.getAsJsonObject("data");
            JsonArray subSubJsonObject =subJsonObject.getAsJsonArray("comment_list");
            CommentList=gson.fromJson(subSubJsonObject,new TypeToken<List<PerComment> >(){}.getType());
            Log.d("Hello",""+CommentList.size());
            int cnt=1;
            for(PerComment perComment:CommentList){
                Log.d("Hello",""+cnt);cnt++;
                Log.d("Hello",perComment.getMail_address());
                Log.d("Hello",perComment.getExhibition_score());
                Log.d("Hello",perComment.getTime());
                Log.d("Hello","---------------------");
            }

    }

}
