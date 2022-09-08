package cc.piner.accountbook.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * <p>createDate 22-9-8</p>
 * <p>fileName   PreferencesUtil</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class PreferencesUtil {

    public static final String PREFS_NAME = "perference";
    public static final String USER_ID_INT = "userid";

    public void saveStringPreference(Context context, String key, String value){
        SharedPreferences userInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public void saveIntPreference(Context context, String key, int value){
        SharedPreferences userInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getStringPerference(Context context, String key, String defValue){
        SharedPreferences userInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return userInfo.getString(key, defValue);
    }

    public int getIntPerference(Context context, String key, int defValue){
        SharedPreferences userInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return userInfo.getInt(key, defValue);
    }

    public void removeUserInfo(Context context, String key){
        SharedPreferences userInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.remove(key);
        editor.apply();
    }

    private void clearUserInfo(Context context){
        SharedPreferences userInfo = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.clear();
        editor.apply();
    }
}
