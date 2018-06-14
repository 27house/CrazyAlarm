package com.sunn.xhui.crazyalarm.ui.garden;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.Constant;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.event.RefreshAlarmListEvent;
import com.sunn.xhui.crazyalarm.utils.MyTools;
import com.sunn.xhui.crazyalarm.utils.SharedPreHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统的SettingsActivity太难搞
 *
 * @author XHui.sun
 * created at 2018/5/23 0023  14:57
 */
public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.tv_version_result)
	TextView tvVersionResult;
	@BindView(R.id.ll_time_version)
	LinearLayout llTimeVersion;
	@BindView(R.id.tv_notify_tip)
	TextView tvNotifyTip;
	@BindView(R.id.ll_notify)
	LinearLayout llNotify;
	@BindView(R.id.tv_time_format_result)
	TextView tvTimeFormatResult;
	@BindView(R.id.ll_time_format)
	LinearLayout llTimeFormat;
	@BindView(R.id.sc_notify)
	SwitchCompat scNotify;
	@BindView(R.id.sc_time_format)
	SwitchCompat scTimeFormat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		toolbar.setTitle(R.string.title_activity_settings);

		tvVersionResult.setText(MyTools.getVersion(this));

		scTimeFormat.setChecked(MyTools.isTimeFormat24(this));
		scTimeFormat.setOnCheckedChangeListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.ll_time_version)
	public void clickTimeVersion(View view) {

	}

	@OnClick(R.id.ll_notify)
	public void clickNotify(View view) {

	}

	@OnClick(R.id.ll_time_format)
	public void clickTimeFormat(View view) {

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
			case R.id.sc_notify:

				break;
			case R.id.sc_time_format:
				SharedPreHelper.getInstance(this).put(Constant.SPF_TIME_FORMAT, isChecked);
				EventBus.getDefault().post(new RefreshAlarmListEvent(true));
				break;
			default:
				break;
		}
	}
}
