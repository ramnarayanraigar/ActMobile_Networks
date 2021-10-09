package com.ramnarayan.actmobilenetworks.util;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
