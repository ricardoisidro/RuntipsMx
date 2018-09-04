package com.runtips.ricardo.runtipsmx.Classes;

import android.content.SharedPreferences;

public class Session {

    public static String getUserMailPrefs(SharedPreferences preferences){
        return preferences.getString("email", "");
    }

    public static String getUserPassPrefs(SharedPreferences preferences){
        return preferences.getString("pass", "");
    }

    public static String getUserNamePrefs(SharedPreferences preferences){
        return preferences.getString("name", "");
    }


    public static void removeSharedPreferences(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("email");
        editor.remove("pass");
        editor.remove("name");
        editor.apply();
    }

    public static void saveSharedPreferences(SharedPreferences preferences,
                                             String mail,
                                             String password,
                                             String name){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", mail);
        editor.putString("pass", password);
        editor.putString("name", name);
        editor.apply();
    }
}
