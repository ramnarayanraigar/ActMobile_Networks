package com.ramnarayan.actmobilenetworks.usecase;

import android.content.Context;
import com.ramnarayan.actmobilenetworks.interfaces.NetWorkResponseListener;
import com.ramnarayan.actmobilenetworks.repository.CountryRepository;

public class CountryNameUseCase {
    public static void getCountryNameList(Context context, NetWorkResponseListener netWorkResponseListener) {
        CountryRepository.getCountryNameList(context, netWorkResponseListener);
    }
}
