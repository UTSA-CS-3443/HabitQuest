package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class CheckLogin {
    private static final String PREF_NAME = "UserPrefs";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    /**
     * Set the login status in SharedPreferences
     * @param context The application context
     * @param loggedIn True if the user is logged in, false otherwise
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.apply();
    }

    /**
     * Check if the user is logged in
     * @param context The application context
     * @return True if the user is logged in, false otherwise
     */
    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
