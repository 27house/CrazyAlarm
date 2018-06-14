package com.sunn.xhui.crazyalarm.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQLHelper {

	public static void writeData(Context context) {
		try {
			AlarmDbHelper helper = new AlarmDbHelper(context);
			SQLiteDatabase db = helper.getReadableDatabase();
			InputStream in = context.getAssets().open("words.sql");
			String sqlUpdate = null;
			try {
				sqlUpdate = readTextFromSDcard(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assert sqlUpdate != null;
			String[] s = sqlUpdate.split(";");
			for (String value : s) {
				if (!TextUtils.isEmpty(value)) {
					db.execSQL(value);
				}
			}
			in.close();
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
	}


	/**
	 * 按行读取txt
	 *
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private static String readTextFromSDcard(InputStream is) throws Exception {
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuilder buffer = new StringBuilder("");
		String str;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
