package modularization.libraries.dataSource.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Reza Amozadeh on 1/24/2017.
 */

public class PreferenceDataSource {

    public static final String PREF_NAME = "ModularArchitecture";

    public static final int MODE = Context.MODE_PRIVATE;

    // PREF KEYS
    public static final String KEY_LANGUAGE = "LANGUAGE";
    public static final String KEY_VIBRATE = "VIBRATE";
    public static final String KEY_SESSION = "SESSION";
    public static final String KEY_MOBILE = "KEY_MOBILE";
    public static final String KEY_PROFILE_ID_ROOM = "KEY_PROFILE_ID_ROOM";
    public static final String KEY_OTP = "KEY_OTP";

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }
}
