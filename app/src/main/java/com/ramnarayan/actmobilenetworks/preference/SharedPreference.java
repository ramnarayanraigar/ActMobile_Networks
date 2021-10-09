package com.ramnarayan.actmobilenetworks.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static final String PREF_COUNTRY_CODE = "country_code";
    private static final String PREF_COUNTRY_NAME = "country_name";
    private static final String PREF_COUNTRY_FLAG_URL = "country_flag_url";


    public static void setCountryName(Context context, String countryName) {
        SharedPreferences preference = context.getSharedPreferences(PREF_COUNTRY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(PREF_COUNTRY_NAME, countryName);
        editor.apply();
    }

    public static String getCountryName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_COUNTRY_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_COUNTRY_NAME, "");
    }

    public static void setCountryCode(Context context, String countryCode) {
        SharedPreferences preference = context.getSharedPreferences(PREF_COUNTRY_CODE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(PREF_COUNTRY_CODE, countryCode);
        editor.apply();
    }

    public static String getCountryCode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_COUNTRY_CODE, Context.MODE_PRIVATE);
        return preferences.getString(PREF_COUNTRY_CODE, "");
    }

    public static void setCountryFlagUrl(Context context, String countryFlagUrl) {
        SharedPreferences preference = context.getSharedPreferences(PREF_COUNTRY_FLAG_URL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(PREF_COUNTRY_FLAG_URL, countryFlagUrl);
        editor.apply();
    }

    public static String getCountryFlagUrl(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_COUNTRY_FLAG_URL, Context.MODE_PRIVATE);
        return preferences.getString(PREF_COUNTRY_FLAG_URL, "");
    }
}
