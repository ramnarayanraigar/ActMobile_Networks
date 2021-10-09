package com.ramnarayan.actmobilenetworks.application;

import com.ramnarayan.actmobilenetworks.network.NetWorkManager;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance(getApplicationContext());
    }

}
