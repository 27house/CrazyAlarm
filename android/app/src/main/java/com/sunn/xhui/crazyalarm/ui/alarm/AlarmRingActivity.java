package com.sunn.xhui.crazyalarm.ui.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Alarm;
import com.sunn.xhui.crazyalarm.data.AlarmRepeat;
import com.sunn.xhui.crazyalarm.db.DatabaseHelper;
import com.sunn.xhui.crazyalarm.receiver.AlarmReceiver;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 响铃界面
 *
 * @author XHui.sun
 * created at 2018/5/17 0017  14:28
 */
public class AlarmRingActivity extends AppCompatActivity {
	@BindView(R.id.tv_decs)
	TextView tvDecs;
	private MediaPlayer mediaPlayer;
	private Alarm alarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_ring);
		ButterKnife.bind(this);
		alarm = (Alarm) getIntent().getSerializableExtra(AddAlarmActivity.EXTRA_ALARM);
		if (alarm == null) {
			finish();
			return;
		}

		tvDecs.setText(alarm.getDesc());
		//时间一到跳转Activity, 在这个Activity中播放音乐
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		try {
			if (alarm.getVoice() != null) {
				mediaPlayer.setDataSource(this, Uri.parse(alarm.getVoice().getRingtone()));
				mediaPlayer.prepare();
				mediaPlayer.start();
				mediaPlayer.setLooping(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//获取系统默认铃声的Uri
	private Uri getSystemDefultRingtoneUri() {
		return RingtoneManager.getActualDefaultRingtoneUri(this,
				RingtoneManager.TYPE_RINGTONE);
	}

	public void pause(View view) {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		if (alarm.getAlarmRepeat().getType() == AlarmRepeat.TYPE_ONCE) {
			alarm.setOpen(false);
			DatabaseHelper.getInstance(this).putAlarm(alarm);
		}
		finish();
	}

	public void stop(View view) {
		if (alarm.getAlarmGame() != null || alarm.getAlarmGame().getId() == 0) {
			Intent intent = new Intent(this, WebGameActivity.class);
			intent.putExtra(WebGameActivity.EXTRA_TASK, alarm.getAlarmGame());
			startActivity(intent);
		}
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, alarm.getId(), intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		//取消警报
		if (am != null) {
			am.cancel(pi);
		}
		pause(view);
	}
}
