package com.buct.museumguide.ui.FragmentForMain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.buct.museumguide.bean.Education;

public class EducationDetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Education> mEduLivaData = new MutableLiveData<>();
    public LiveData<Education> getEduLivaData() {
        if(mEduLivaData == null) {
            mEduLivaData = new MutableLiveData<>();
        }
        return mEduLivaData;
    }
    public void setEduLivaData(Education education) {
        if(mEduLivaData!=null) {
            mEduLivaData.setValue(education);
        }
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        mEduLivaData=null;
    }
}
