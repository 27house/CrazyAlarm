package com.sunn.xhui.crazyalarm.ui.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Dynamic;
import com.sunn.xhui.crazyalarm.mpv.contract.DynamicContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.DynamicPresenter;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.ui.adapter.DynamicListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 社区-广场
 *
 * @author XHui.sun
 * created at 2018/5/24 0024  11:49
 */
public class CommunityFragment extends BaseFragment implements DynamicContract.ListView, SwipeRefreshLayout.OnRefreshListener {

	@BindView(R.id.main_content)
	CoordinatorLayout mainContent;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.appbar)
	AppBarLayout appbar;
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout swipeRefreshLayout;
	private DynamicListAdapter mAdapter;
	private DynamicPresenter presenter;

	@Override
	protected int initLayoutId() {
		return R.layout.fragment_community;
	}

	@Override
	protected void activityCreated() {
		presenter = new DynamicPresenter(this);
		initView();
		presenter.getDynamicList(0, 50);
	}

	private void initView() {
		toolbar.setTitle("");
		activity.setSupportActionBar(toolbar);//替换系统的actionBar
		//设置TabLayout
		for (int i = 1; i < 20; i++) {
			tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
		}
		//TabLayout的切换监听
		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				//切换的时候更新RecyclerView
//				initData(tab.getPosition()+1);
//				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
		recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		mAdapter = new DynamicListAdapter(activity);
		recyclerView.setAdapter(mAdapter);
		swipeRefreshLayout.setOnRefreshListener(this);
	}

	public void addDynamic() {
		Snackbar.make(mainContent, "哈哈哈哈哈", Snackbar.LENGTH_SHORT).show();
		startActivity(new Intent(activity, AddDynamicActivity.class));
	}

	@Override
	public void showTip(String tip) {
		Snackbar.make(swipeRefreshLayout, tip, Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void dismissLoad() {
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void returnDynamicList(List<Dynamic> list) {
		mAdapter.setDynamicList(list);
	}

	@Override
	public void onRefresh() {
		presenter.getDynamicList(0,30);
	}
}
