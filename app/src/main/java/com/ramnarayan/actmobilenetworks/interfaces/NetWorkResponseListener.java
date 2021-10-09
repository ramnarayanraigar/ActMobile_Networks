package com.ramnarayan.actmobilenetworks.interfaces;

import com.android.volley.VolleyError;

public interface NetWorkResponseListener {
    void onSuccess(String response);
    void onError(VolleyError volleyError, String message);
}
