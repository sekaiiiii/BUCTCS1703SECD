package com.buct.museumguide.ui.FragmentForMain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.bean.Exhibition;

public class ExhibitionDetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Exhibition> mExhiLivaData = new MutableLiveData<>();
    public LiveData<Exhibition> getExhiLivaData() {
        if(mExhiLivaData == null) {
            mExhiLivaData = new MutableLiveData<>();
        }
        return mExhiLivaData;
    }
    public void setExhiLivaData(Exhibition exhibition) {
        if(mExhiLivaData!=null) {
            mExhiLivaData.setValue(exhibition);
        }
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        mExhiLivaData=null;
    }
}
