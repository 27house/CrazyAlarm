package com.sunn.xhui.crazyalarm.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.event.RefreshAlarmListEvent;
import com.sunn.xhui.crazyalarm.ui.adapter.MainFragmentAdapter;
import com.sunn.xhui.crazyalarm.ui.community.CommunityFragment;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 *
 * @author XHui.sun
 * created at 2018/5/14 0014  15:12
 */

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.view_pager)
	ViewPager viewPager;
	@BindView(R.id.navigation)
	BottomNavigationView navigation;
	@BindView(R.id.container)
	LinearLayout container;
	private List<Fragment> listFragment = new ArrayList<>();
	private int currentPage;
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_home:
					gotoPage(0);
					return true;
				case R.id.navigation_dashboard:
					gotoPage(1);
					return true;
				case R.id.navigation_notifications:
					gotoPage(2);
					return true;
				default:
					break;
			}
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		EventBus.getDefault().register(this);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
		listFragment.add(new AlarmListFragment());
		listFragment.add(new CommunityFragment());
		listFragment.add(new GardenFragment());
		MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), listFragment);
		viewPager.setOffscreenPageLimit(3);
		viewPager.setAdapter(mainFragmentAdapter);
	}

	public void gotoPage(int pos) {
		if (currentPage != pos) {
			try {
				currentPage = pos;
				viewPager.setCurrentItem(pos, false);
			} catch (Exception e) {
				LogUtil.e(e.getMessage());
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			Fragment fragment = listFragment.get(currentPage);
			if (fragment != null) {
				fragment.onActivityResult(requestCode, resultCode, data);
			}
		} catch (Exception e) {
			LogUtil.e(e.getMessage());
		}
	}

	@Subscribe
	public void onEventMainThread(RefreshAlarmListEvent event) {
		if (event.isStartSet()) {
			AlarmListFragment fragment = (AlarmListFragment) listFragment.get(0);
			if (fragment != null) {
				fragment.refreshData();
			}
		}
	}
}
