package com.huseen.abo.aita1998.timemagment.adtional;

import android.content.Context;
import android.content.SharedPreferences;

public class YourPreference {

    public static final String SCREEN1 = "SCREEN1";
    public static final String SCREEN2 = "SCREEN2";
    public static final String SCREEN3 = "SCREEN3";


    private static YourPreference yourPreference;

    private SharedPreferences sharedPreferences;

    public static YourPreference getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new YourPreference(context);
        }
        return yourPreference;
    }

    private YourPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("screen", Context.MODE_PRIVATE);
    }

    public void addScreen(String screen, int i) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        if (i == 0) {
            prefsEditor.putString(SCREEN1, screen);
        } else if (i == 1) {
            prefsEditor.putString(SCREEN2, screen);
        } else if (i == 2) {
            prefsEditor.putString(SCREEN3, screen);
        }
        prefsEditor.apply();
    }

    public String getScreen1() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(SCREEN1, "");
        }
        return "";
    }

    public String getScreen2() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(SCREEN2, "");
        }
        return "";
    }

    public String getScreen3() {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(SCREEN3, "");
        }
        return "";
    }

}
