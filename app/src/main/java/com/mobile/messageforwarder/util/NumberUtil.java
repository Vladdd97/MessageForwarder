package com.mobile.messageforwarder.util;

import android.content.Context;
import android.content.SharedPreferences;


public class NumberUtil {

    private NumberUtil(){}

    public static void saveFromNumbers(String numbers){
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.putString("fromNumbers",numbers);
        editor.apply();
    }

    public static void saveToNumbers(String numbers){
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.putString("toNumbers",numbers);
        editor.apply();
    }

    public static String getFromNumbers(){
        SharedPreferences sharedPreferences = ContextProvider.getAppContext().getSharedPreferences("MFPreferences",Context.MODE_PRIVATE);
        return sharedPreferences.getString("fromNumbers","no fromNumbers");
    }

    public static String getToNumbers(){
        SharedPreferences sharedPreferences = ContextProvider.getAppContext().getSharedPreferences("MFPreferences",Context.MODE_PRIVATE);
        return  sharedPreferences.getString("toNumbers","no toNumbers");
    }
}
