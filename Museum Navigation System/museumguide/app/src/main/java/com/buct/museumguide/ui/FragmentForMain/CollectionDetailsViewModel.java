package com.buct.museumguide.ui.FragmentForMain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.bean.Collection;

public class CollectionDetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Collection> mCollLivaData = new MutableLiveData<>();
    public LiveData<Collection> getCollLivaData() {
        if(mCollLivaData == null) {
            mCollLivaData = new MutableLiveData<>();
        }
        return mCollLivaData;
    }
    public void setCollLivaData(Collection collection) {
        if(mCollLivaData!=null) {
            mCollLivaData.setValue(collection);
        }
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        mCollLivaData=null;
    }
}
