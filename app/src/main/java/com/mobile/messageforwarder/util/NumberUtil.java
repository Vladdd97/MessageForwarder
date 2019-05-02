package com.mobile.messageforwarder.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;


public class NumberUtil {

    public static String SEPARATOR = ",";

    private NumberUtil() {
    }

    public static void saveNumber(NumberType numberType, String number) {
        SharedPreferences sharedPreferences = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE);
        String numbers = sharedPreferences.getString(numberType.toString(), "") + (number + SEPARATOR);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(numberType.toString(), numbers);
        editor.apply();
    }

    public static ArrayList<String> getNumbers(NumberType numberType) {
        SharedPreferences sharedPreferences = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE);
        return new ArrayList<>(Arrays.asList(sharedPreferences.getString(numberType.toString(), "no " + numberType + " numbers").split(SEPARATOR)));
    }


    public static void deleteNumber(NumberType numberType, String number) {
        StringBuilder numbers = new StringBuilder();
        ArrayList<String> numberList = getNumbers(numberType);
        // delete number
        numberList.remove(number);

        // create String numbers from numberList elements using SEPARATOR
        for (String n : numberList) {
            numbers.append(n).append(SEPARATOR);
        }
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.putString(numberType.toString(), numbers.toString());
        editor.apply();

    }

    public static void deleteAllNumbers(NumberType numberType) {
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.remove(numberType.toString());
        editor.apply();
    }

}
