package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

public class PreferenceManager {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String LIST_KEY = "my_list";

    public static void saveExpenses(Context context, List<Expense> expenseList)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(expenseList);

        editor.putString(LIST_KEY, json);
        editor.apply();
    }



    public static List<Expense> loadExpenses(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(LIST_KEY, null);

        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<ArrayList<Expense>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
