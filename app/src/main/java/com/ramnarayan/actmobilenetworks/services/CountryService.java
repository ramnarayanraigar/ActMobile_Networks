package com.ramnarayan.actmobilenetworks.services;

import android.content.Context;

import com.android.volley.VolleyError;
import com.ramnarayan.actmobilenetworks.interfaces.NetWorkResponseListener;
import com.ramnarayan.actmobilenetworks.network.NetWorkManager;

import org.json.JSONObject;

public class CountryService {

    public static void getCountryNameList(Context context, int requestType, JSONObject parameter,
                                          String url, String tag, boolean isProgressBar, NetWorkResponseListener listener) {

        NetWorkManager.getInstance().volleyJsonRequest(context, requestType, parameter, url, tag, isProgressBar
                , new NetWorkResponseListener() {
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
