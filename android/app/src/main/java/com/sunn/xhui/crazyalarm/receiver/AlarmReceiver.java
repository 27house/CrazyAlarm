package com.sunn.xhui.crazyalarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sunn.xhui.crazyalarm.data.Alarm;
import com.sunn.xhui.crazyalarm.data.AlarmRepeat;
import com.sunn.xhui.crazyalarm.db.DatabaseHelper;
import com.sunn.xhui.crazyalarm.ui.alarm.AddAlarmActivity;
import com.sunn.xhui.crazyalarm.ui.alarm.AlarmRingActivity;
import com.sunn.xhui.crazyalarm.utils.AlarmUtils;

import java.util.Calendar;

/**
 * 时间到后，AlarmManager会发送广播，在这里接受响铃消息
 *
 * @author XHui.sun
 * created at 2018/5/17 0017  14:25
 */
public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		int id = intent.getIntExtra(AlarmUtils.EXTRA_ALARM_ID, -1);
		Log.i("test", "----onReceive----" + id);
		if (id >= 0) {
			Alarm alarm = DatabaseHelper.getInstance(context).getAlarmForId(id);
			Calendar calendar = Calendar.getInstance();
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			if (alarm != null && alarm.isOpen()) {
				Log.i("test", "----onReceive----" + alarm.getDesc());
				switch (alarm.getAlarmRepeat().getType()) {
					case AlarmRepeat.TYPE_EVERY_DAY:
						// goto
						startRing(context, alarm);
						break;
					case AlarmRepeat.TYPE_WEEK:
						if (week == Calendar.SUNDAY || week == Calendar.SATURDAY) {
							// goto
							startRing(context, alarm);
						}
						break;
					case AlarmRepeat.TYPE_WORK_DAY:
						if (week != Calendar.SUNDAY && week != Calendar.SATURDAY) {
							// goto
							startRing(context, alarm);
						}
						break;
					case AlarmRepeat.TYPE_CUSTOM:
						if (alarm.getAlarmRepeat().getWeeks().contains("" + week)) {
							// goto
							startRing(context, alarm);
						}
						break;
					default:
						startRing(context, alarm);
						break;
				}
			}
		}
	}

	private void startRing(Context context, Alarm alarm) {
		//跳转到Activity n //广播接受者中（跳转Activity）
		Intent start = new Intent(context, AlarmRingActivity.class);
		start.putExtra(AddAlarmActivity.EXTRA_ALARM, alarm);
		//给Intent设置标志位
		start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(start);
	}
}
