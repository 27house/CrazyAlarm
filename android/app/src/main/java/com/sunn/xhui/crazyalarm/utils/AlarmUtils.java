package com.sunn.xhui.crazyalarm.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.sunn.xhui.crazyalarm.data.Alarm;
import com.sunn.xhui.crazyalarm.receiver.AlarmReceiver;

import java.util.Calendar;


/**
 * @author XHui.sun
 * created at 2018/5/22 0022  10:27
 */

public class AlarmUtils {
	public static final String EXTRA_ALARM_ID = "EXTRA_ALARM_ID";

	public static void setAlarm(Context context, Alarm alarm) {
		if (!alarm.isOpen()) {
			LogUtil.e("这个闹钟已关闭--" + alarm.getDesc());
			return;
		}
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
		if (alarmManager != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(alarm.getTime());
			Calendar current = Calendar.getInstance();
			if (current.getTimeInMillis() > calendar.getTimeInMillis()) {
				// AlarmManager 设定时间小于当前时间，立即触发广播解决方案
				calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
			}
			// 触发time
			long triggerAtMillis = calendar.getTimeInMillis();
			//间隔time -- 单位毫秒
			long intervalMillis;
			Intent intent = new Intent(context, AlarmReceiver.class);
			intent.putExtra(EXTRA_ALARM_ID, alarm.getId());
			//时间一到，发送广播（闹钟响了）
			LogUtil.e("---设置响铃Intent--" + alarm.getId());
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, 0);
			//该方法用于设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
//			alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis + 24 * 60 * 60 *1000, pendingIntent);
			//该方法用于设置重复闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟首次执行时间，第三个参数表示闹钟两次执行的间隔时间，第三个参数表示闹钟响应动作。
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, 60 * 1000, pendingIntent);
		}
	}
}
