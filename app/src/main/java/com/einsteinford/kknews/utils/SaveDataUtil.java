package com.einsteinford.kknews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KK on 2016-08-22.
 */
public class SaveDataUtil {

    public static void write2SDFromString(Context context,String data,String fileName) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save2SharedPreferences(Context context, HashMap<String,String> Cities, String fileName){
        SharedPreferences pref = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        ArrayList<String> mCityNameArrayList = new ArrayList<String>();
        ArrayList<String> mCityIdArrayList = new ArrayList<String>();
        mCityIdArrayList.addAll(Cities.keySet());
        for (int s = 0; s < Cities.size(); s++) {
            mCityNameArrayList.add(Cities.get(mCityIdArrayList.get(s)));
        }
        for (int i = 0; i < Cities.size(); i++) {
            editor.putString(mCityIdArrayList.get(i),mCityNameArrayList.get(i));
        }
        editor.apply();
    }
    public static HashMap<String,String> getFromSharedPreferences(Context context,String fileName){
        SharedPreferences pref = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        return (HashMap<String, String>) pref.getAll();
    }
}
