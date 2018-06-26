package com.sunn.xhui.crazyalarm.ui.main;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Alarm;
import com.sunn.xhui.crazyalarm.db.DatabaseHelper;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.ui.adapter.AlarmListAdapter;
import com.sunn.xhui.crazyalarm.ui.alarm.AddAlarmActivity;
import com.sunn.xhui.crazyalarm.ui.garden.SettingsActivity;
import com.sunn.xhui.crazyalarm.utils.AlarmUtils;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author XHui.sun
 * created at 2018/5/22 0022  14:11
 */
public class AlarmListFragment extends BaseFragment implements Toolbar.OnMenuItemClickListener, SwipeRefreshLayout.OnRefreshListener {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	@BindView(R.id.fab)
	FloatingActionButton fab;
	Unbinder unbinder;
	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout swipeRefreshLayout;
	private AlarmListAdapter alarmListAdapter;

	private List<Alarm> alarmList = new ArrayList<>();


	@Override
	protected int initLayoutId() {
		return R.layout.fragment_alarm_list;
	}

	@Override
	protected void activityCreated() {
		activity.setSupportActionBar(toolbar);
		setHasOptionsMenu(true);
		toolbar.setTitle(getString(R.string.title_home));
		toolbar.setOnMenuItemClickListener(this);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(activity, AddAlarmActivity.class));
			}
		});
		alarmListAdapter = new AlarmListAdapter(activity);
		recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(alarmListAdapter);
		swipeRefreshLayout.setOnRefreshListener(this);
		refreshData();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_alarm_list, menu);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_setting:
				LogUtil.e("点击了弹出菜单1");
				startActivity(new Intent(activity, SettingsActivity.class));
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public void onRefresh() {
		swipeRefreshLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(false);
			}
		}, 1500);
	}

	public void refreshData() {
		alarmList = DatabaseHelper.getInstance(activity).getAlarmList();
		alarmListAdapter.setAlarmList(alarmList);
		for (Alarm alarm : alarmList) {
			AlarmUtils.setAlarm(activity, alarm);
		}
	}
}
