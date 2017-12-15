package proj.ifsp.tcc1.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Tiago on 01/12/2017.
 */

public class PreferencesHelper {

    private static final String filename = "mqs_pref_file";

    public static void saveIntPrefence (Context context, String key, int value){

        SharedPreferences sp = context.getSharedPreferences(filename,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(key,value);

        editor.commit();
    }

    public static void saveStringPreference (Context context, String key, String value){

        SharedPreferences sp = context.getSharedPreferences(filename,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString(key,value);

        editor.commit();
    }

    public static int getIntPreference(Context context, String key){

        SharedPreferences sp = context.getSharedPreferences(filename,Context.MODE_PRIVATE);

        return sp.getInt(key,0);

    }

    public static String getStringPreference(Context context, String key){

        SharedPreferences sp = context.getSharedPreferences(filename,Context.MODE_PRIVATE);

        return sp.getString(key,"");

    }
}
