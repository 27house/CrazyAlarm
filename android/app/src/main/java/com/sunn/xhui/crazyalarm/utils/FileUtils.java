package com.sunn.xhui.crazyalarm.utils;

import android.content.Context;
import android.text.TextUtils;

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
}
