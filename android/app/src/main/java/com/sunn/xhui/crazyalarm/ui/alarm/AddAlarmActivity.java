package com.sunn.xhui.crazyalarm.ui.alarm;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Alarm;
import com.sunn.xhui.crazyalarm.data.AlarmGame;
import com.sunn.xhui.crazyalarm.data.AlarmRepeat;
import com.sunn.xhui.crazyalarm.data.Voice;
import com.sunn.xhui.crazyalarm.db.DatabaseHelper;
import com.sunn.xhui.crazyalarm.event.RefreshAlarmListEvent;
import com.sunn.xhui.crazyalarm.utils.LogUtil;
import com.sunn.xhui.crazyalarm.utils.MyTools;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XHui.sun
 * created at 2018/5/15 0015  14:59
 */

public class AddAlarmActivity extends AppCompatActivity implements SelectRepeatFragment.SelectRepeatCallback,
		SelectVoiceFragment.SelectVoiceCallback,
		SelectGameFragment.SelectGameCallback,
		Toolbar.OnMenuItemClickListener {
	public static final String EXTRA_ALARM = "EXTRA_ALARM";
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.timePicker)
	TimePicker timePicker;
	@BindView(R.id.tv_repeat_result)
	TextView tvRepeatResult;
	@BindView(R.id.tv_voice_result)
	TextView tvVoiceResult;
	@BindView(R.id.tv_game_result)
	TextView tvGameResult;
	@BindView(R.id.tv_note_count)
	TextView tvNoteCount;
	@BindView(R.id.et_note)
	EditText etNote;
	@BindView(R.id.rl_content)
	RelativeLayout rlContent;
	@BindView(R.id.btn_delete)
	Button btnDelete;
	private Alarm alarm;
	public Calendar currentTime;
	public AlarmRepeat currentRepeat;
	public Voice currentVoice;
	public AlarmGame currentGame;
	private String mainTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_alarm);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		initData();
		initView();
	}

	private void initView() {
		toolbar.setTitle(mainTitle);
		toolbar.setOnMenuItemClickListener(this);
		tvRepeatResult.setText(currentRepeat.getShowStr());
		tvVoiceResult.setText(currentVoice.getTitle());
		tvGameResult.setText(currentGame.getName());
		etNote.setText(alarm.getDesc());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			timePicker.setHour(currentTime.get(Calendar.HOUR_OF_DAY));
			timePicker.setMinute(currentTime.get(Calendar.MINUTE));
		} else {
			timePicker.setCurrentHour(currentTime.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(currentTime.get(Calendar.MINUTE));
		}
		timePicker.setIs24HourView(MyTools.isTimeFormat24(this));
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
				currentTime.set(Calendar.MINUTE, minute);
			}
		});
	}

	private void initData() {
		alarm = (Alarm) getIntent().getSerializableExtra(EXTRA_ALARM);
		if (alarm == null) {
			alarm = new Alarm();
			currentTime = Calendar.getInstance();
			currentRepeat = DatabaseHelper.getInstance(this).getDefaultRepeat();
			currentVoice = DatabaseHelper.getInstance(this).getDefaultVoice(this);
			currentGame = DatabaseHelper.getInstance(this).getDefaultGame();
			mainTitle = getString(R.string.title_activity_add_alarm);
			btnDelete.setVisibility(View.GONE);
		} else {
			currentTime = Calendar.getInstance();
			currentTime.setTimeInMillis(alarm.getTime());
			currentRepeat = alarm.getAlarmRepeat();
			currentVoice = alarm.getVoice();
			currentGame = alarm.getAlarmGame();
			mainTitle = getString(R.string.title_activity_add_alarm_1);
			btnDelete.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			FragmentManager fm = getSupportFragmentManager();
			Fragment fragment = fm.findFragmentById(R.id.rl_content);
			if (fragment == null) {
				finish();
			} else {
				toolbar.setTitle(R.string.title_activity_add_alarm);
				rlContent.setVisibility(View.GONE);
				fm.popBackStackImmediate();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		getMenuInflater().inflate(R.menu.menu_alarm_add, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean show = toolbar.getTitle().toString().equals(getString(R.string.title_activity_add_alarm))
				|| toolbar.getTitle().toString().equals(getString(R.string.title_activity_add_alarm_1))
				|| toolbar.getTitle().toString().equals(getString(R.string.select_game));
		menu.findItem(R.id.menu_save).setVisible(show);
		invalidateOptionsMenu();
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_save:
				Fragment fragment = getSupportFragmentManager().findFragmentByTag(SelectGameFragment.class.getSimpleName());
				if (fragment != null && fragment instanceof SelectGameFragment && fragment.isVisible()) {
					// saveAlarmGame
					((SelectGameFragment) fragment).commit();
				} else {
					saveAlarm();

				}

				break;
			default:
				break;
		}
		return true;
	}

	private void saveAlarm() {
		LogUtil.e("点击了右边图标");
		LogUtil.e("hour -- " + currentTime.get(Calendar.HOUR_OF_DAY) + ", minute --- " + currentTime.get(Calendar.MINUTE));
		alarm.setTime(currentTime.getTimeInMillis());
		alarm.setAlarmRepeat(currentRepeat);
		alarm.setVoice(currentVoice);
		alarm.setAlarmGame(currentGame);
		alarm.setDesc(etNote.getText().toString());
		alarm.setOpen(true);
		// 通知首页刷新数据并设置闹钟，此时才能得到闹钟id
		DatabaseHelper.getInstance(this).putAlarm(alarm);
		EventBus.getDefault().post(new RefreshAlarmListEvent(true));
		finish();
	}

	@OnClick(R.id.ll_repeat)
	public void clickRepeat(View view) {
		toolbar.setTitle(R.string.select_repeat);
		rlContent.setVisibility(View.VISIBLE);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.rl_content, new SelectRepeatFragment(), SelectRepeatFragment.class.getSimpleName());
		ft.addToBackStack(SelectRepeatFragment.class.getSimpleName());
		ft.commit();
	}

	@OnClick(R.id.ll_voice)
	public void clickVoice(View view) {
		toolbar.setTitle(R.string.select_voice);
		rlContent.setVisibility(View.VISIBLE);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.rl_content, new SelectVoiceFragment(), SelectVoiceFragment.class.getSimpleName());
		ft.addToBackStack(SelectVoiceFragment.class.getSimpleName());
		ft.commit();
	}

	@OnClick(R.id.ll_game)
	public void clickGame(View view) {
		toolbar.setTitle(R.string.select_game);
		rlContent.setVisibility(View.VISIBLE);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.rl_content, new SelectGameFragment(), SelectGameFragment.class.getSimpleName());
		ft.addToBackStack(SelectGameFragment.class.getSimpleName());
		ft.commit();
	}


	@OnClick(R.id.btn_delete)
	public void clickDelete(View view) {
		DatabaseHelper.getInstance(this).deleteAlarm(alarm.getId());
		EventBus.getDefault().post(new RefreshAlarmListEvent(true));
		finish();
	}

	private void returnActivity() {
		toolbar.setTitle(R.string.title_activity_add_alarm);
		rlContent.setVisibility(View.GONE);
		getSupportFragmentManager().popBackStackImmediate();
	}

	@Override
	public void selectedRepeat(AlarmRepeat repeat) {
		currentRepeat = repeat;
		tvRepeatResult.setText(repeat.getShowStr());
		returnActivity();
	}


	@Override
	public void selectedVoice(Voice voice) {
		currentVoice = voice;
		tvVoiceResult.setText(voice.getTitle());
		returnActivity();
	}

	@Override
	public void selectedVoice(AlarmGame game) {
		currentGame = game;
		tvGameResult.setText(game.getName());
		returnActivity();
	}

	/**
	 * Take care of popping the fragment back stack or finishing the activity
	 * as appropriate.
	 */
	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		final boolean isStateSaved = fm.isStateSaved();
		if (isStateSaved && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
			return;
		}
		toolbar.setTitle(mainTitle);
		rlContent.setVisibility(View.GONE);
		if (isStateSaved || !fm.popBackStackImmediate()) {
			super.onBackPressed();
		}
	}

}
