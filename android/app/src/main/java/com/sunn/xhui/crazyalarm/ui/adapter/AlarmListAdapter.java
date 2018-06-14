package com.sunn.xhui.crazyalarm.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Alarm;
import com.sunn.xhui.crazyalarm.db.DatabaseHelper;
import com.sunn.xhui.crazyalarm.ui.alarm.AddAlarmActivity;
import com.sunn.xhui.crazyalarm.utils.AlarmUtils;
import com.sunn.xhui.crazyalarm.utils.MyTools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XHui.sun
 * created at 2018/5/15 0015  14:42
 */
public class AlarmListAdapter extends BaseRecycleAdapter {

	private Context context;

	public AlarmListAdapter(Context context) {
		super(context);
		this.context = context;
	}

	public void setAlarmList(List<Alarm> list) {
		super.setDataList(list);
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false);
		return new ViewHolder(view);
	}

	public class ViewHolder extends BaseRecViewHolder implements CompoundButton.OnCheckedChangeListener {

		@BindView(R.id.tv_time_m)
		TextView tvTimeM;
		@BindView(R.id.tv_time)
		TextView tvTime;
		@BindView(R.id.tv_note)
		TextView tvNote;
		@BindView(R.id.tv_music)
		TextView tvMusic;
		@BindView(R.id.switchCompat)
		SwitchCompat switchCompat;
		@BindView(R.id.cardView)
		CardView cardView;
		Alarm alarm;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@SuppressLint("SetTextI18n")
		@Override
		public void setData(Object item) {
			super.setData(item);
			alarm = (Alarm) item;
			tvNote.setText(alarm.getDesc() + "," + alarm.getAlarmRepeat().getShowStr());
			switchCompat.setChecked(alarm.isOpen());
			if (MyTools.isTimeFormat24(context)) {
				tvTimeM.setVisibility(View.GONE);
				tvTime.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(alarm.getTime()));
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(alarm.getTime());
				tvTimeM.setVisibility(View.VISIBLE);
				tvTimeM.setText(getMStr(calendar));
				tvTime.setText(new SimpleDateFormat("hh:mm", Locale.getDefault()).format(alarm.getTime()));
			}
			if (alarm.getVoice() != null) {
				tvMusic.setVisibility(View.VISIBLE);
				tvMusic.setText(alarm.getVoice().getTitle());
			} else {
				tvMusic.setVisibility(View.GONE);
			}
			switchCompat.setOnCheckedChangeListener(this);
		}

		private String getMStr(Calendar calendar) {
			if (calendar.get(Calendar.HOUR_OF_DAY) < 8) {
				return "早上";
			} else if (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
				return "上午";
			} else if (calendar.get(Calendar.HOUR_OF_DAY) == 12) {
				return "中午";
			} else if (calendar.get(Calendar.HOUR_OF_DAY) < 20) {
				return "下午";
			} else {
				return "晚上";
			}
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			alarm.setOpen(isChecked);
			DatabaseHelper.getInstance(context).putAlarm(alarm);
			if (isChecked) {
				AlarmUtils.setAlarm(context, alarm);
			}
		}

		@OnClick(R.id.cardView)
		public void clickCardView(View view) {
			Intent intent = new Intent(context, AddAlarmActivity.class);
			intent.putExtra(AddAlarmActivity.EXTRA_ALARM, alarm);
			context.startActivity(intent);
		}
	}

}
