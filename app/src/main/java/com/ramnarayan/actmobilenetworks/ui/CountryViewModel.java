package com.ramnarayan.actmobilenetworks.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.VolleyError;
import com.ramnarayan.actmobilenetworks.interfaces.NetWorkResponseListener;
import com.ramnarayan.actmobilenetworks.usecase.CountryNameUseCase;
import com.ramnarayan.actmobilenetworks.util.Constants;

public class CountryViewModel extends ViewModel {
    private final MutableLiveData<String[]> mutableCountryName = new MutableLiveData<>();

    public LiveData<String[]> getCountryNameList() {
        return mutableCountryName;
    }

    public void getCountryNameList(Context context) {
        String[] country = new String[2];
        CountryNameUseCase.getCountryNameList(context, new NetWorkResponseListener() {
            @Override
            public void onSuccess(String response) {
                country[0] = Constants.OK;
                country[1] = response;

                mutableCountryName.setValue(country);
            }

            @Override
            public void onError(VolleyError volleyError, String message) {
                country[0] = Constants.FAILED;
                country[1] = message;
                mutableCountryName.setValue(country);
            }
        });
    }
}
