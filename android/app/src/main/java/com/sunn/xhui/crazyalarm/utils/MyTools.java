package com.sunn.xhui.crazyalarm.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.FileProvider;
import android.util.Base64;

import com.sunn.xhui.crazyalarm.Constant;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author XHui.sun
 * created at 2018/5/24 0024  11:22
 */
public class MyTools {


	public static boolean isTimeFormat24(Context context) {
		return SharedPreHelper.getInstance(context).get(Constant.SPF_TIME_FORMAT, false);
	}

	public static String getVersion(Context context) {
		String version = "1.0.0.0";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			version = info.versionName;
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
		return "v" + version;
	}
	/**
	 * 通过Base32将Bitmap转换成Base64字符串
	 */
	public static String Bitmap2StrByBase64(Context context, String path) {
		String base64 = "";
		InputStream in = null;
		try {
			Uri fileUri = getUri(context, path);
			ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(fileUri, "r");
			if (pfd != null) {
				FileDescriptor fd = pfd.getFileDescriptor();
				in = new FileInputStream(fd); // 读入原文件
				byte[] bytes = new byte[in.available()];
				int length = in.read(bytes);
				base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
			}
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LogUtil.e(e.getMessage());
			}
		}
		return base64;
	}


	public static final String AUTHORITY = "com.sunn.xhui.crazyalarm.fileProvider";
	public static Uri getUri(Context context, String path) {
		File file = new File(path);
		Uri cropImageUri;
		if (path.startsWith("http")) {
			cropImageUri = Uri.parse(path);
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				// "包名.fileprovider"即是在清单文件中配置的authorities
				cropImageUri = FileProvider.getUriForFile(context, AUTHORITY, file);
			} else {
				cropImageUri = Uri.fromFile(new File(path));
			}
		}
		return cropImageUri;
	}
}
