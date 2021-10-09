package com.ramnarayan.actmobilenetworks.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ramnarayan.actmobilenetworks.interfaces.NetWorkResponseListener;
import com.ramnarayan.actmobilenetworks.util.LogCustom;
import com.ramnarayan.actmobilenetworks.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NetWorkManager {
    private static RequestQueue requestQueue;
    private static NetWorkManager netWorkManager;
    private ProgressDialog progressDialog;

    private NetWorkManager() {

    }

    public static synchronized void getInstance(Context context) {
        if (netWorkManager == null) {
            netWorkManager = new NetWorkManager();
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public static synchronized NetWorkManager getInstance() {
        if (netWorkManager == null) {
            throw new IllegalStateException(NetWorkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }

        return netWorkManager;
    }

    public void volleyJsonRequest(final Context context, int methodType /* Get or Post */,
                                  final JSONObject jsonParameter, String apiUrl,
                                  final String TAG, boolean isProgressBar, final NetWorkResponseListener netWorkResponseListener) {

        handleProgressDialog(context, isProgressBar);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(methodType, apiUrl, jsonParameter, response -> {

            printLog(TAG, jsonParameter.toString(), apiUrl, response);

            /* remove progressDialog */
            handleProgressDialog(context, false);

            netWorkResponseListener.onSuccess(response.toString());
        }, error -> {

            // remove progressDialog
            handleProgressDialog(context, false);

            // print log
            printLog(TAG, jsonParameter.toString(), apiUrl, Objects.requireNonNull(error.getMessage()));

            // parse 400 response
            NetworkResponse networkResponse = error.networkResponse;

            if (error instanceof ServerError && networkResponse != null) {
                try {
                    String response = new String(networkResponse.data,
                            HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"));

                    printLog(TAG, jsonParameter.toString(), apiUrl, response);

                    if (isValidJson(response)) {
                        netWorkResponseListener.onSuccess(response);
                    } else {
                        netWorkResponseListener.onError(error, "Something went wrong");
                        Utility.toast(context,"Something went wrong");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return;
            }


            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Utility.toast(context,"Internet connection is too slow to handle this request");
                netWorkResponseListener.onError(error, "Internet connection is too slow to handle this request");
            } else if (error instanceof AuthFailureError) {
                Utility.toast(context,"Authentication error, please try again later");
                netWorkResponseListener.onError(error, "Authentication error, please try again later");
            } else if (error instanceof ServerError) {
                Utility.toast(context,"Server error");
                netWorkResponseListener.onError(error, "Server error");
            } else if (error instanceof NetworkError) {
                Utility.toast(context,"Network error");
                netWorkResponseListener.onError(error, "Network error");
            } else if (error instanceof ParseError) {
                Utility.toast(context,"Parse error");
                netWorkResponseListener.onError(error, "Parse error");
            } else {
                Utility.toast(context,"Something went wrong, please try again later");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return new HashMap<>();
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjectRequest.setTag(TAG);

        requestQueue.add(jsonObjectRequest);
        requestQueue.getCache().clear();
    }

    private void printLog(String tag, String parameter, String url, Object response) {
        LogCustom.i(tag + " API Parameter: ", parameter);
        LogCustom.i(tag + " API Url: ", url);
        LogCustom.i(tag + " API Response: ", response.toString());
    }

    private boolean isValidJson(String response) {
        try {
            new JSONObject(response);
        } catch (JSONException e) {
            try {
                new JSONArray(response);
            } catch (JSONException ex) {
                ex.printStackTrace();

                return false;
            }
            e.printStackTrace();
        }

        return true;
    }

    private void handleProgressDialog(final Context context, boolean isShowProgress) {
        try {
            if (isShowProgress) {
                if (progressDialog == null) {
                    progressDialog = ProgressDialog.show(context, "", "Please wait...");
                }
            } else {
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
