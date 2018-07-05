package com.sunn.xhui.crazyalarm.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.sunn.xhui.crazyalarm.ui.main.WebAppActivity;

import java.io.File;

public class FileUtils {

	public static String getFilesPath(Context context, String folderName) {
		try {
			File imageF = new File(context.getFilesDir().getPath() + "/" + folderName);
			if (!imageF.exists()) {
				imageF.mkdirs();
			}
			return imageF.getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getCachePath(Activity activity) {
		return activity.getCacheDir().getPath();
	}
}
