package com.buct.museumguide.ui.FragmentForUsers;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buct.museumguide.R;

public class modify_username extends Fragment {

    private ModifyUsernameViewModel mViewModel;
    private SharedPreferences infos;
    public static final String TAG="Modify_Username";
    public static modify_username newInstance() {
        return new modify_username();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.modify_username_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        infos=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        mViewModel=new ViewModelProvider(this).get(ModifyUsernameViewModel.class);
        Button button_OK=getView().findViewById(R.id.bt_define_modify_username);
        Button button_back=getView().findViewById(R.id.bt_backup);
        final EditText name=getView().findViewById(R.id.etext_newname);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        button_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_name=name.getText().toString();
                if(new_name.equals("")) Toast.makeText(getActivity(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                else if(new_name.length()>18||new_name.length()<2)Toast.makeText(getActivity(),"用户名长度不正确",Toast.LENGTH_SHORT).show();
                else{
                    mViewModel.getState(new_name,getActivity(),getView()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if(s.equals("0"))
                                Toast.makeText(getActivity(),"用户名修改失败",Toast.LENGTH_SHORT).show();
                            else{
                                Toast.makeText(getActivity(),"用户名修改成功",Toast.LENGTH_SHORT).show();
                                infos.edit().putString("user",new_name).apply();
                                Navigation.findNavController(view).popBackStack();
                            }
                        }
                    });
                }
            }
        });
        // TODO: Use the ViewModel
    }

}
