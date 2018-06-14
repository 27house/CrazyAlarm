package com.sunn.xhui.crazyalarm.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sunn.xhui.crazyalarm.Constant;

/**
 * @author XHui.sun
 * created at 2018/5/24 0024  10:38
 */
public class SharedPreHelper {
	private static SharedPreHelper instance;
	private SharedPreferences sp;

	public static SharedPreHelper getInstance(Context context) {
		return new SharedPreHelper(context);
	}

	private SharedPreHelper(Context context) {
		sp = context.getSharedPreferences(Constant.FILE_NAME_SPF, Context.MODE_PRIVATE);
	}

	public void put(String key, String value) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public void put(String key, int value) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public void put(String key, float value) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putFloat(key, value);
		editor.apply();
	}

	public void put(String key, boolean value) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public String get(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public String get(String key) {
		return get(key, "");
	}

	public boolean get(String key, boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	public int get(String key, int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public float get(String key, float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}


}
