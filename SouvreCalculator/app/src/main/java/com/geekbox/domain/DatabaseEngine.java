package com.geekbox.domain;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class DatabaseEngine {

    private Context context;

    public DatabaseEngine(Context context){
        this.context = context;
    }

    public String getLanguage(){
        SharedPreferences preferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language;
        language = preferences.getString("Language", "");

        return language;
    }

    public void saveLanguage(String languageID){
        // Save data to prferences.
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("Language", languageID);
        editor.apply();
    }
}
