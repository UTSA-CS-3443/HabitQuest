package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.content.SharedPreferences;

public class CheckLogin {
    private static final String PREF_NAME = "UserPrefs";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
