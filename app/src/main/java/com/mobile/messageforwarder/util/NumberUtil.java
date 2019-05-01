package com.mobile.messageforwarder.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class NumberUtil {

    public static String SEPARATOR = ",";

    private NumberUtil() {
    }

    public static void saveFromNumber(String number) {
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.putString(NumberType.FROM_NUMBER.toString(), number + SEPARATOR);
        editor.apply();
    }

    public static void saveToNumber(String number) {
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.putString(NumberType.TO_NUMBER.toString(), number + SEPARATOR);
        editor.apply();
    }

    public static List<String> getFromNumbers() {
        SharedPreferences sharedPreferences = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE);
        List<String> numbers = Arrays.asList(sharedPreferences.getString("fromNumbers", "no fromNumbers").split(SEPARATOR));
        return numbers;
    }

    public static List<String> getToNumbers() {
        SharedPreferences sharedPreferences = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE);
        List<String> numbers = Arrays.asList(sharedPreferences.getString("toNumbers", "no toNumbers").split(SEPARATOR));
        return numbers;
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
        for (String n : numberList){
            numbers.append(n).append(SEPARATOR);
        }
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.putString(numberType.toString(), numbers.toString());
        editor.apply();

    }

    public static void deleteAllNumbers(){
        SharedPreferences.Editor editor = ContextProvider.getAppContext().getSharedPreferences("MFPreferences", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

}
