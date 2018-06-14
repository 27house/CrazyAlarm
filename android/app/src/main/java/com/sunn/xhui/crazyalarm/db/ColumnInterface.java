package com.sunn.xhui.crazyalarm.db;

/**
 * 建表
 *
 * @author XHui.sun
 * created at 2018/5/21 0021  10:28
 */
public interface ColumnInterface {
	String COL_ACCOUNT = "ACCOUNT";

	/**
	 * 闹钟相关设置信息表
	 *
	 * @author XHui.sun
	 * created at 2018/5/21 0021  10:29
	 */
	interface AlarmTab {
		String TABLE_NAME = "ALARM";
		String COL_TIME = "COL_TIME";
		String COL_REP = "COL_REP";
		String COL_MUS_TYPE = "COL_MUS_TYPE";
		String COL_MUS_TITLE = "COL_MUS_TITLE";
		String COL_MUS_PATH = "COL_MUS_PATH";
		String COL_GAM_ID = "COL_GAM_ID";
		String COL_GAM_NAME = "COL_GAM_NAME";
		String COL_GAM_TYPE = "COL_GAM_TYPE";
		String COL_DESC = "COL_DESC";

		String COL_OPEN = "COL_OPEN";

		String CREATE_TABLE = "create table if not exists " + TABLE_NAME
				+ "( _id integer primary key autoincrement ,"
				+ COL_TIME + " integer,"
				+ COL_REP + " text,"
				+ COL_MUS_TITLE + " text,"
				+ COL_MUS_TYPE + " integer,"
				+ COL_MUS_PATH + " text,"
				+ COL_GAM_ID + " integer,"
				+ COL_GAM_NAME + " text,"
				+ COL_GAM_TYPE + " integer,"
				+ COL_OPEN + " integer,"
				+ COL_DESC + " text" + ");";
	}

	/**
	 * 铃声列表
	 *
	 * @author XHui.sun
	 * created at 2018/5/21 0021  11:24
	 */
	interface AlarmVoiceTab {
		String TABLE_NAME = "ALARM_VOICE";
		String COL_MUS_ID = "COL_MUS_ID";
		String COL_MUS_NAME = "COL_MUS_NAME";
		String COL_MUS_PATH = "COL_MUS_PATH";
		String CREATE_TABLE = "create table if not exists " + TABLE_NAME
				+ "( _id integer primary key autoincrement ,"
				+ COL_MUS_ID + " integer,"
				+ COL_MUS_NAME + " text,"
				+ COL_MUS_PATH + " text" + ");";
	}

	/**
	 * 游戏列表
	 *
	 * @author XHui.sun
	 * created at 2018/5/25 0025  15:39
	 */
	interface AlarmGameTab {
		String TABLE_NAME = "ALARM_GAME";
		String COL_GAME_NAME = "COL_GAME_NAME";
		String COL_GAME_TYPE = "COL_GAME_TYPE";
		String CREATE_TABLE = "create table if not exists " + TABLE_NAME
				+ "( _id integer primary key autoincrement ,"
				+ COL_GAME_NAME + " text,"
				+ COL_GAME_TYPE + " integer" + ");";
	}
}
