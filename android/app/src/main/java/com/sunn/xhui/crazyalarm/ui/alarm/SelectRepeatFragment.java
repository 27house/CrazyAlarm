package com.sunn.xhui.crazyalarm.ui.alarm;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.AlarmRepeat;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择循环
 *
 * @author XHui.sun
 * created at 2018/5/16 0016  9:45
 */
public class SelectRepeatFragment extends BaseFragment {


	@BindView(R.id.cv_once)
	CardView cvOnce;
	@BindView(R.id.cv_every_day)
	CardView cvEveryDay;
	@BindView(R.id.cv_week)
	CardView cvWeek;
	@BindView(R.id.cv_work_day)
	CardView cvWorkDay;
	@BindView(R.id.cb_mon)
	CheckBox cbMon;
	@BindView(R.id.cb_tue)
	CheckBox cbTue;
	@BindView(R.id.cb_wed)
	CheckBox cbWed;
	@BindView(R.id.cb_thu)
	CheckBox cbThu;
	@BindView(R.id.cb_fri)
	CheckBox cbFri;
	@BindView(R.id.cb_sat)
	CheckBox cbSat;
	@BindView(R.id.cb_sun)
	CheckBox cbSun;
	@BindView(R.id.btn_create)
	Button btnCreate;
	private SelectRepeatCallback callback;

	private Map<Integer, String> weekMap = new TreeMap<>();
	AlarmRepeat alarmRepeat;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof SelectRepeatCallback) {
			callback = (SelectRepeatCallback) context;
		}
	}

	@Override
	protected int initLayoutId() {
		return R.layout.fragment_create_repeat;
	}

	@Override
	protected void activityCreated() {
		alarmRepeat = new AlarmRepeat();
		cbMon.setOnCheckedChangeListener(checkListener);
		cbTue.setOnCheckedChangeListener(checkListener);
		cbWed.setOnCheckedChangeListener(checkListener);
		cbThu.setOnCheckedChangeListener(checkListener);
		cbFri.setOnCheckedChangeListener(checkListener);
		cbSat.setOnCheckedChangeListener(checkListener);
		cbSun.setOnCheckedChangeListener(checkListener);

	}

	CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int key = getIntFromStr(buttonView.getText().toString());
			if (isChecked && !weekMap.containsKey(key)) {
				weekMap.put(key, buttonView.getText().toString());
			} else {
				weekMap.remove(key);
			}
		}
	};

	private int getIntFromStr(String s) {
		if (getString(R.string.monday).equals(s)) {
			return Calendar.MONDAY;
		} else if (getString(R.string.tuesday).equals(s)) {
			return Calendar.TUESDAY;
		} else if (getString(R.string.wednesday).equals(s)) {
			return Calendar.WEDNESDAY;
		} else if (getString(R.string.thursday).equals(s)) {
			return Calendar.THURSDAY;
		} else if (getString(R.string.friday).equals(s)) {
			return Calendar.FRIDAY;
		} else if (getString(R.string.saturday).equals(s)) {
			return Calendar.SATURDAY;
		} else {
			return Calendar.SUNDAY;
		}
	}

	@OnClick(R.id.cv_once)
	public void clickOnce(View view) {
		alarmRepeat.setType(AlarmRepeat.TYPE_ONCE);
		callback.selectedRepeat(alarmRepeat);
	}

	@OnClick(R.id.cv_every_day)
	public void clickEveryDay(View view) {
		alarmRepeat.setType(AlarmRepeat.TYPE_EVERY_DAY);
		callback.selectedRepeat(alarmRepeat);
	}

	@OnClick(R.id.cv_week)
	public void clickWeek(View view) {
		alarmRepeat.setType(AlarmRepeat.TYPE_WEEK);
		callback.selectedRepeat(alarmRepeat);
	}

	@OnClick(R.id.cv_work_day)
	public void clickWorkDay(View view) {
		alarmRepeat.setType(AlarmRepeat.TYPE_WORK_DAY);
		callback.selectedRepeat(alarmRepeat);
	}

	@OnClick(R.id.btn_create)
	public void clickCreate(View view) {
		if (weekMap.isEmpty()) {
			Snackbar.make(view, R.string.select_repeat_error, Snackbar.LENGTH_LONG).show();
			return;
		}
		alarmRepeat.setType(AlarmRepeat.TYPE_CUSTOM);
		Set<Integer> keySet = weekMap.keySet();
		Iterator<Integer> iter = keySet.iterator();
		StringBuilder str = new StringBuilder();
		StringBuilder weekInt = new StringBuilder();
		while (iter.hasNext()) {
			Integer key = iter.next();
			str.append(weekMap.get(key));
			str.append(" ");
			System.out.println(key + ":" + weekMap.get(key));
			weekInt.append(key);
		}
		alarmRepeat.setWeeks(weekInt.toString());
		alarmRepeat.setShowStr(str.substring(0, str.lastIndexOf(" ")));
		callback.selectedRepeat(alarmRepeat);
	}

	public interface SelectRepeatCallback {
		/**
		 * 返回选择的类型
		 *
		 * @param type 类型指向详见Alarm.class
		 */
		void selectedRepeat(AlarmRepeat type);
	}

}
