package com.sunn.xhui.crazyalarm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;

import com.sunn.xhui.crazyalarm.data.Alarm;
import com.sunn.xhui.crazyalarm.data.AlarmGame;
import com.sunn.xhui.crazyalarm.data.AlarmRepeat;
import com.sunn.xhui.crazyalarm.data.Voice;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作助手
 *
 * @author XHui.sun
 * created at 2018/5/21 0021  10:40
 */
@SuppressWarnings("unused")
public class DatabaseHelper extends SerializedUtils implements ColumnInterface {

	private AlarmDbHelper helper;
	private SQLiteDatabase sqlDb;
	private static DatabaseHelper instance = null;
	private Context context;

	public static DatabaseHelper getInstance(Context ctx) {
		if (instance == null) {
			instance = new DatabaseHelper(ctx);
		}
		return instance;
	}

	private DatabaseHelper(Context ctx) {
		helper = new AlarmDbHelper(ctx);
		this.context = ctx;
	}

	/**
	 * 创建新的闹钟
	 *
	 * @param alarm Alarm
	 */
	public void putAlarm(Alarm alarm) {
		try {
			ContentValues values = new ContentValues();
			// 自动增长的id，不需要再put
			values.put(AlarmTab.COL_TIME, alarm.getTime());
			values.put(AlarmTab.COL_REP, getSerializedObject(alarm.getAlarmRepeat()));
			// 铃声
			values.put(AlarmTab.COL_MUS_TITLE, alarm.getVoice().getTitle());
			values.put(AlarmTab.COL_MUS_TYPE, alarm.getVoice().getType());
			if (alarm.getVoice().getType() == Voice.TYPE_RING_TONE) {
				values.put(AlarmTab.COL_MUS_PATH, alarm.getVoice().getRingtone());
			} else {
				values.put(AlarmTab.COL_MUS_PATH, alarm.getVoice().getPath());
			}
			// 响铃任务
			values.put(AlarmTab.COL_GAM_TYPE, alarm.getAlarmGame().getType());
			values.put(AlarmTab.COL_GAM_NAME, alarm.getAlarmGame().getName());
			values.put(AlarmTab.COL_GAM_ID, alarm.getAlarmGame().getId());

			values.put(AlarmTab.COL_DESC, alarm.getDesc());
			values.put(AlarmTab.COL_OPEN, alarm.isOpen() ? 0 : 1);
			if (getAlarmForId(alarm.getId()) == null) {
				sqlDb = helper.getReadableDatabase();
				sqlDb.insert(AlarmTab.TABLE_NAME, null, values);
				sqlDb.close();
			} else {
				sqlDb = helper.getReadableDatabase();
				int result = sqlDb.update(AlarmTab.TABLE_NAME, values, "_id =?", new String[]{alarm.getId() + ""});
				sqlDb.close();
			}
		} catch (Exception e) {
			LogUtil.e(e.getLocalizedMessage());
		}
	}

	/**
	 * 获取闹钟列表
	 *
	 * @return List<Alarm>
	 */
	public List<Alarm> getAlarmList() {
		sqlDb = helper.getReadableDatabase();
		List<Alarm> alarmList = new ArrayList<>();
		try {
			Cursor cursor = sqlDb.query(AlarmTab.TABLE_NAME, null, null, null,
					null, null, AlarmTab.COL_TIME + " asc", null);
			while (cursor.moveToNext()) {
				alarmList.add(cursorToAlarm(cursor));
			}
			cursor.close();
			sqlDb.close();
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
		return alarmList;
	}

	public Alarm getAlarmForId(int id) {
		try {
			sqlDb = helper.getReadableDatabase();
			Cursor cursor = sqlDb.query(AlarmTab.TABLE_NAME, null, "_id =?",
					new String[]{id + ""}, null, null, null);
			//noinspection LoopStatementThatDoesntLoop
			while (cursor.moveToNext()) {
				return cursorToAlarm(cursor);
			}
			cursor.close();
			sqlDb.close();
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
		return null;
	}

	public void deleteAlarm(int id) {
		try {
			sqlDb = helper.getReadableDatabase();
			sqlDb.delete(AlarmTab.TABLE_NAME, "_id =?", new String[]{"" + id});
			sqlDb.close();
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
	}

	private Alarm cursorToAlarm(Cursor cursor) {
		Alarm alarm = new Alarm();
		alarm.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		alarm.setTime(cursor.getLong(cursor.getColumnIndex(AlarmTab.COL_TIME)));
		byte[] repeat = cursor.getBlob(cursor.getColumnIndex(AlarmTab.COL_REP));
		if (repeat != null) {
			alarm.setAlarmRepeat((AlarmRepeat) readSerializedObject(repeat));
		}

		Voice voice = new Voice(cursor.getInt(cursor.getColumnIndex(ColumnInterface.AlarmTab.COL_MUS_TYPE)));
		voice.setPath(cursor.getString(cursor.getColumnIndex(ColumnInterface.AlarmTab.COL_MUS_PATH)));
		voice.setTitle(cursor.getString(cursor.getColumnIndex(ColumnInterface.AlarmTab.COL_MUS_TITLE)));
		alarm.setVoice(voice);

		AlarmGame game =new AlarmGame();
		game.setName(cursor.getString(cursor.getColumnIndex(ColumnInterface.AlarmTab.COL_GAM_NAME)));
		game.setType(cursor.getInt(cursor.getColumnIndex(ColumnInterface.AlarmTab.COL_GAM_TYPE)));
		game.setId(cursor.getInt(cursor.getColumnIndex(ColumnInterface.AlarmTab.COL_GAM_ID)));
		alarm.setAlarmGame(game);

		alarm.setDesc(cursor.getString(cursor.getColumnIndex(AlarmTab.COL_DESC)));
		alarm.setOpen(cursor.getInt(cursor.getColumnIndex(AlarmTab.COL_OPEN)) == 0);
		return alarm;
	}

	public AlarmRepeat getDefaultRepeat() {
		AlarmRepeat alarmRepeat = new AlarmRepeat();
		alarmRepeat.setType(AlarmRepeat.TYPE_ONCE);
		return alarmRepeat;
	}

	public Voice getDefaultVoice(Context context) {
		Voice voice = null;
		// 铃声管理器
		RingtoneManager ringtoneManager = new RingtoneManager(context);
		//获取铃声表,根据表名取值
		Cursor cursor = ringtoneManager.getCursor();
		while (cursor != null && cursor.moveToNext()) {
			voice = new Voice(Voice.TYPE_RING_TONE);
			voice.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			voice.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			voice.setRingtone(ringtoneManager.getRingtoneUri(cursor.getPosition()).toString());
			break;
		}
		if (cursor != null) {
			cursor.close();
		}
		return voice;
	}

	public AlarmGame getDefaultGame() {
		return getAlarmGameList().get(0);
	}

	public List<AlarmGame> getAlarmGameList() {
		List<AlarmGame> games = new ArrayList<>();
		AlarmGame game = new AlarmGame();
		game.setId(1);
		game.setName("愤怒的小鸟");

		AlarmGame game2 = new AlarmGame();
		game2.setId(2);
		game2.setName("背单词");
		games.add(game2);

		AlarmGame game3 = new AlarmGame();
		game3.setId(3);
		game3.setName("贪吃蛇");
		games.add(game3);
		return games;
	}

	private AlarmGame cursorToGame(Cursor cursor) {
		AlarmGame game = new AlarmGame();
		game.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		game.setName(cursor.getString(cursor.getColumnIndex(AlarmGameTab.COL_GAME_NAME)));
		game.setType(cursor.getInt(cursor.getColumnIndex(AlarmGameTab.COL_GAME_TYPE)));
		return game;
	}

	/**
	 * 删除用户
	 * @param account 用户账号
	 */
  /*  private void deleteUser(String account) {
        sqlDb = helper.getReadableDatabase();
        String selection = COL_ACCOUNT + "=?";
        sqlDb.delete(UserInfoTab.TABLE_NAME, selection, new String[]{account});
        sqlDb.close();
    }*/

	/**
	 * 查找用户
	 * @param account 用户账号
	 * @return UserInfo
	 */
    /*public UserInfo getUser(String account) {
        UserInfo user = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(UserInfoTab.TABLE_NAME, null, ConstactsTab.COL_ACCOUNT + "=?",
                new String[]{account}, null, null, null);
        //noinspection LoopStatementThatDoesntLoop
        while (cursor.moveToNext()) {
            user = new UserInfo();
            user.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(UserInfoTab.COL_ID))));
            user.setAccount(cursor.getString(cursor.getColumnIndex(COL_ACCOUNT)));
            user.setAccName(cursor.getString(cursor.getColumnIndex(UserInfoTab.COL_ACC_NAME)));
            user.setAccIcon(cursor.getString(cursor.getColumnIndex(UserInfoTab.COL_ACC_ICON)));
            user.setAccNickName(cursor.getString(cursor
                    .getColumnIndex(UserInfoTab.COL_ACC_NICKNAME)));
            user.setAccPwd(cursor.getString(cursor.getColumnIndex(UserInfoTab.COL_ACC_PWD)));
            user.setAccAge(cursor.getInt(cursor.getColumnIndex(UserInfoTab.COL_ACC_AGE)));
            user.setAccSex(cursor.getInt(cursor.getColumnIndex(UserInfoTab.COL_ACC_SEX)));
            break;
        }
        cursor.close();
        db.close();
        return user;
    }*/

	/**
	 * 获取推送消息列表
	 * @param account 登录账号
	 * @return 消息列表
	 */
    /*public List<MessageEntity> getMessageList(String account) {
        sqlDb = helper.getReadableDatabase();
        List<MessageEntity> messageList = new ArrayList<>();
        try {
            String selection = COL_ACCOUNT + " =? ";
            Cursor cursor = sqlDb.query(MessageTab.TABLE_NAME, null, selection, new String[]{account},
                    null, null, MessageTab.COL_TIME + " desc", null);
            while (cursor.moveToNext()) {
                MessageEntity message = new MessageEntity();
                message.account = cursor.getString(cursor.getColumnIndex(COL_ACCOUNT));
                message.content = cursor.getString(cursor.getColumnIndex(MessageTab.COL_CONTENT));
                message.title = cursor.getString(cursor.getColumnIndex(MessageTab.COL_TITLE));
                message.time = cursor.getString(cursor.getColumnIndex(MessageTab.COL_TIME));
                message.isRead = cursor.getInt(cursor.getColumnIndex(MessageTab.COL_IS_READ));
                message.msgId = cursor.getInt(cursor.getColumnIndex(MessageTab.COL_ID));

                byte [] tmp = cursor.getBlob(cursor.getColumnIndex(MessageTab.COL_OBJECT));
                if (tmp != null) {
                    message.msgObject = readSerializedObject(tmp);
                }

                messageList.add(message);
            }
            cursor.close();
            sqlDb.close();
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
        return messageList;
    }*/

	/**
	 * 添加推送消息至本地
	 * @param message 消息对象
	 */
    /*public void addMessage(MessageEntity message) {
        sqlDb = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ACCOUNT, message.account);
        values.put(MessageTab.COL_CONTENT, message.content);
        values.put(MessageTab.COL_TITLE, message.title);
        values.put(MessageTab.COL_TIME, message.time);
        values.put(MessageTab.COL_IS_READ, message.isRead);
        values.put(MessageTab.COL_ID, message.msgId);

        byte[] byteArr = getSerializedObject((Serializable) message.msgObject);
        if (byteArr != null) {
            values.put(MessageTab.COL_OBJECT, byteArr);
        }

        sqlDb.insert(MessageTab.TABLE_NAME, null, values);
        sqlDb.close();
    }*/

	/**
	 * 移除红点（标记消息为已读状态）
	 * @param account 登录账号
	 * @param msgId 消息ID
	 */
 /*   public void removeRead(String account, long msgId) {
        sqlDb = helper.getReadableDatabase();
        String selection = COL_ACCOUNT + "=? AND " + MessageTab.COL_ID + "=?";
        ContentValues values = new ContentValues();
        values.put(MessageTab.COL_IS_READ, 1);
        int result = sqlDb.update(MessageTab.TABLE_NAME, values, selection, new String[]{account, msgId + ""});
        sqlDb.close();
    }*/

	/**
	 * 移除红点（标记消息为已读状态）
	 * @param account 登录账号
	 */
    /*public void removeReadByIpcId(String account, long ipcid) {
        List<MessageEntity> hasUnRead = hasUnReadMessage(account);
        if (hasUnRead.size() > 0) {
            for (MessageEntity m:hasUnRead) {
                DeviceInfo deviceInfo = (DeviceInfo) m.msgObject;
                if (deviceInfo != null && deviceInfo.getIpcid() == ipcid) {
                    removeRead(account,m.msgId);
                }
            }
        }
    }*/

	/**
	 * 是否有未读消息
	 * @param account 登录账号
	 * @return 未读消息列表
	 */
    /*public List<MessageEntity> hasUnReadMessage(String account) {
        List<MessageEntity> unReadList = new ArrayList<>();
        List<MessageEntity> messageList = getMessageList(account);
        for (MessageEntity message : messageList) {
            if (message.isRead == 0) {
                unReadList.add(message);
            }
        }
        return unReadList;
    }*/

	/**
	 * 删除所有消息
	 * @param account 登录账号
	 */
   /* public void deleteAllMessageByAccount(String account) {
        sqlDb = helper.getReadableDatabase();
        String selection = COL_ACCOUNT + "=?";
        sqlDb.delete(MessageTab.TABLE_NAME, selection, new String[]{account});
        sqlDb.close();
    }*/
}
