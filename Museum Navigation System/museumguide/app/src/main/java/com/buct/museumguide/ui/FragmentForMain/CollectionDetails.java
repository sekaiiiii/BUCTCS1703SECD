package com.buct.museumguide.ui.FragmentForMain;

import android.os.Bundle;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

public class CollectionDetails extends Fragment {

    private CollectionDetailsViewModel collectionDetailsViewModel;
    private ImageView detail_coll_img;
    private TextView detail_coll_name;
    private TextView detail_coll_material_content;
    private TextView detail_coll_content;

    public static CollectionDetails newInstance() {
        return new CollectionDetails();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        collectionDetailsViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CollectionDetailsViewModel.class);
        View root = inflater.inflate(R.layout.collection_details_fragment, container, false);
        detail_coll_img = root.findViewById(R.id.detail_coll_img);
        detail_coll_name = root.findViewById(R.id.detail_coll_name);
        detail_coll_material_content = root.findViewById(R.id.detail_coll_material_content);
        detail_coll_content = root.findViewById(R.id.detail_coll_content);
        collectionDetailsViewModel.getCollLivaData().observe(getViewLifecycleOwner(), collection-> {
            if(!collection.getName().equals("")&&!collection.getName().equals("null"))
                detail_coll_name.setText(collection.getName());
            if(!collection.getMaterial().equals("")&&!collection.getMaterial().equals("null"))
                detail_coll_material_content.setText(collection.getMaterial());
            if(!collection.getContent().equals("")&&!collection.getContent().equals("null"))
                detail_coll_content.setText(collection.getContent());
            try {
                Glide.with(this)
                        .load(getImageUrl(collection.getImage_list()))
                        .apply(new RequestOptions().error(R.drawable.emptyphoto))
                        .into(detail_coll_img);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
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
