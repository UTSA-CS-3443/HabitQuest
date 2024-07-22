package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.content.SharedPreferences;

public class CheckLogin {

    private static final String PREFS_NAME = "user_prefs";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_LOGGED_IN, false);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.apply();
    }
}
