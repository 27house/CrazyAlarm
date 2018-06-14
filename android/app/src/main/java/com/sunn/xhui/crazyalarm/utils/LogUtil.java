package com.sunn.xhui.crazyalarm.utils;

import android.util.Log;

import com.sunn.xhui.crazyalarm.BuildConfig;

/**
 * 打印日志
 *
 * @author XHui.sun
 * created at 2018/5/21 0021  10:35
 */
public class LogUtil {
	private static final String TAG = "xhui";

	public static void e(String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(TAG, msg);
		}
	}

	public static void d(String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(TAG, msg);
		}
	}

	public static void i(String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(TAG, msg);
		}
	}

	public static void w(String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(TAG, msg);
		}
	}

	public static void v(String msg) {
		if (BuildConfig.DEBUG) {
			Log.v(TAG, msg);
		}
	}
}
