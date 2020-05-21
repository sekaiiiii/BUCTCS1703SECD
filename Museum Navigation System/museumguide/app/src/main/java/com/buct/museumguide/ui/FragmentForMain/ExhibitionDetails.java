package com.buct.museumguide.ui.FragmentForMain;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.buct.museumguide.R;
import com.buct.museumguide.ui.FragmentForMain.CommonList.CommonList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

public class ExhibitionDetails extends Fragment {

    private ExhibitionDetailsViewModel exhibitionDetailsViewModel;
    private ImageView detail_exhi_img;
    private TextView detail_exhi_name;
    private TextView detail_exhi_time_content;
    private TextView detail_exhi_content;

    public static ExhibitionDetails newInstance() {
        return new ExhibitionDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        exhibitionDetailsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(ExhibitionDetailsViewModel.class);
        View root = inflater.inflate(R.layout.exhibition_details_fragment, container, false);;
        detail_exhi_img = root.findViewById(R.id.detail_exhi_img);
        detail_exhi_name = root.findViewById(R.id.detail_exhi_name);
        detail_exhi_time_content = root.findViewById(R.id.detail_exhi_time_content);
        detail_exhi_content = root.findViewById(R.id.detail_exhi_content);
        exhibitionDetailsViewModel.getExhiLivaData().observe(getViewLifecycleOwner(), exhibition-> {
            detail_exhi_name.setText(exhibition.getName());
            if(!exhibition.getTime().equals("")&&!exhibition.getTime().equals("null"))
                detail_exhi_time_content.setText(exhibition.getTime());
            if(!exhibition.getContent().equals("")&&!exhibition.getContent().equals("null"))
                detail_exhi_content.setText(exhibition.getContent());
            try {
                Glide.with(this)
                        .load(getImageUrl(exhibition.getImage_list()))
                        .apply(new RequestOptions().error(R.drawable.emptyphoto))
                        .into(detail_exhi_img);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public String getImageUrl(JSONArray imgList) throws JSONException {
        String imgurl = "";
        if(imgList.length()==0){
            imgurl = "";
        }
        else {
            imgurl = "http://192.144.239.176:8080/" + imgList.get(0).toString();
        }
        return imgurl;
    }
}
