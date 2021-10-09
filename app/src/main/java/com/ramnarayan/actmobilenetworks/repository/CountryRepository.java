package com.ramnarayan.actmobilenetworks.repository;

import android.content.Context;

import com.android.volley.VolleyError;
import com.ramnarayan.actmobilenetworks.api.APIUrl;
import com.ramnarayan.actmobilenetworks.interfaces.NetWorkResponseListener;
import com.ramnarayan.actmobilenetworks.services.CountryService;
import com.ramnarayan.actmobilenetworks.util.Constants;

import org.json.JSONObject;

public class CountryRepository {
    private static final String TAG = CountryRepository.class.getSimpleName();

    public static void getCountryNameList(Context context, NetWorkResponseListener listener) {
        JSONObject parameter = new JSONObject();
        String url = APIUrl.URL_GET_COUNTRY_NAMES;

        CountryService.getCountryNameList(context, Constants.GET, parameter, url, TAG, true, new NetWorkResponseListener() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(response);
            }

            @Override
            public void onError(VolleyError volleyError, String message) {
                listener.onError(volleyError, message);
            }
        });
    }
}
