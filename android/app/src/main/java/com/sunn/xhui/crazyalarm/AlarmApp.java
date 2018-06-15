package com.sunn.xhui.crazyalarm;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sunn.xhui.crazyalarm.data.UserInfo;
import com.sunn.xhui.crazyalarm.utils.SharedPreHelper;

import java.io.File;

/**
 * @author XHui.sun
 * created at 2018/5/24 0024  11:00
 */
public class AlarmApp extends Application {

	private static Application app;
	public static String HttpCachePath;

	private static int screenW = 0;
	private static int screenH = 0;
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		File cacheFile = new File(getCacheDir(), "HttpCache");
		HttpCachePath = cacheFile.getPath();
		initFresco(getApplicationContext());
	}

	private static void initFresco(Context context) {
		Fresco.initialize(context);
	}

	public static Context getContext() {
		return app.getApplicationContext();
	}

	public static UserInfo userInfo;

	public static String getAccount() {
		String account = SharedPreHelper.getInstance(getContext()).get(Constant.SPF_USER_ACC);
		if (account == null) {
			account = "";
		}
		return account;
	}

	public static void setAccount(UserInfo info) {
		userInfo = info;
		if (userInfo != null) {
			SharedPreHelper.getInstance(getContext()).put(Constant.SPF_USER_ACC, userInfo.getAccount());
		} else {
			SharedPreHelper.getInstance(getContext()).put(Constant.SPF_USER_ACC, "");
		}
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param aty Context
	 * @return H
	 */
	public static int getScreenH(Context aty) {
		if (aty != null){
			DisplayMetrics dm = aty.getResources().getDisplayMetrics();
			screenH = dm.heightPixels;
		}
		return screenH;
	}

	/**
	 * 获取屏幕宽度
	 *
	 * @param aty Context
	 * @return W
	 */
	public static int getScreenW(Context aty) {
		if (aty != null){
			DisplayMetrics dm = aty.getResources().getDisplayMetrics();
			screenW = dm.widthPixels;
		}
		return screenW;
	}
}
