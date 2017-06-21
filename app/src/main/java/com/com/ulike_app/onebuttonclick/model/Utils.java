package com.com.ulike_app.onebuttonclick.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.com.ulike_app.onebuttonclick.entity.ActionEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by RDL on 20/06/2017.
 */

public class Utils {


    public static ArrayList<ActionEntity> jsonToList(String json) {
        ActionEntity[] configs = new Gson().fromJson(json, ActionEntity[].class);
        return new ArrayList<>(Arrays.asList(configs));
    }

    public static void saveToPref(String jsonResponse, Context context) {
        SharedPreferences sPref = context.getSharedPreferences("CONFIGS", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("JSON", jsonResponse);
        editor.commit();
    }

    public static String getPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("CONFIGS", context.MODE_PRIVATE);
        return sharedPreferences.getString("JSON", "");
    }
}
