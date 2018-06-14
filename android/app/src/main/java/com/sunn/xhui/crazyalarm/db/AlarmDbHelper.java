package com.sunn.xhui.crazyalarm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sunn.xhui.crazyalarm.Constant;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

/**
 * 创建数据库存储闹钟信息
 *
 * @author XHui.sun
 * created at 2018/5/21 0021  10:27
 */
public class AlarmDbHelper extends SQLiteOpenHelper implements ColumnInterface {

	public AlarmDbHelper(Context context) {
		super(context, Constant.FILE_NAME_DB, null, Constant.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(AlarmTab.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
		if (oldVersion < 2) {
			updateToV_2(db);
		}
	}

	/**
	 * 数据库升级到版本2，同时DB_VERSION增加到2
	 *
	 * @param db SQLiteDatabase
	 */
	private void updateToV_2(SQLiteDatabase db) {
//		execSQLIgnoreException(db,MessageTab.SQL_ADD_COL_OBJECT);
	}

	/**
	 * 忽略SQL异常
	 *
	 * @param db  SQLiteDatabase
	 * @param sql String
	 */
	private void execSQLIgnoreException(SQLiteDatabase db, String sql) {
		try {
			db.execSQL(sql);
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
	}
}
