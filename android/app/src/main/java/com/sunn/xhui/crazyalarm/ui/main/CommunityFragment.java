package com.sunn.xhui.crazyalarm.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Dynamic;
import com.sunn.xhui.crazyalarm.mpv.contract.DynamicContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.DynamicPresenter;
import com.sunn.xhui.crazyalarm.net.req.SetDynamicReq;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.ui.adapter.DynamicListAdapter;
import com.sunn.xhui.crazyalarm.ui.community.AddDynamicActivity;
import com.sunn.xhui.crazyalarm.ui.garden.LoginActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 社区-广场
 *
 * @author XHui.sun
 * created at 2018/5/24 0024  11:49
 */
public class CommunityFragment extends BaseFragment implements DynamicContract.ListView,
		SwipeRefreshLayout.OnRefreshListener, DynamicListAdapter.ItemClick {

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
		//替换系统的actionBar
		activity.setSupportActionBar(toolbar);
		//设置TabLayout
		tabLayout.addTab(tabLayout.newTab().setText("关注"));
		tabLayout.addTab(tabLayout.newTab().setText("热门"));
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
		mAdapter = new DynamicListAdapter(activity, this);
		recyclerView.setAdapter(mAdapter);
		swipeRefreshLayout.setOnRefreshListener(this);
	}

	public void addDynamic() {
		if (TextUtils.isEmpty(AlarmApp.getAccount())) {
			startActivity(new Intent(activity, LoginActivity.class));
		} else {
			startActivity(new Intent(activity, AddDynamicActivity.class));
		}
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
		presenter.getDynamicList(0, 30);
	}

	public void refreshData() {
		swipeRefreshLayout.setRefreshing(true);
		presenter.getDynamicList(0, 30);
	}

	@Override
	public void returnSetResult(boolean success, int type, String tip, int did) {
		for (Dynamic d : mAdapter.getDataList()) {
			if (d.getId() == did) {
				switch (type) {
					case SetDynamicReq.TYPE_ADD_LIKE:
						d.setIsLike(1);
						d.setLikeCount(d.getLikeCount() + 1);
						break;
					case SetDynamicReq.TYPE_REMOVE_LIKE:
						d.setIsLike(0);
						d.setLikeCount(d.getLikeCount() - 1);
						break;
					case SetDynamicReq.TYPE_DELETE:
						mAdapter.getDataList().remove(d);
						break;
					default:
						break;
				}
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void clickLike(boolean isLike, int did) {
		SetDynamicReq req = new SetDynamicReq(SetDynamicReq.TYPE_ADD_LIKE);
		if (isLike) {
			req.setType(SetDynamicReq.TYPE_REMOVE_LIKE);
		}
		req.setcId(0);
		req.setdId(did);
		presenter.setDynamic(req);
	}

	@Override
	public void clickComment(int did) {

	}

	@Override
	public void clickDelete(final int did) {
		new AlertDialog.Builder(activity)
				.setMessage("是否删除这条动态？")
				.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SetDynamicReq req = new SetDynamicReq(SetDynamicReq.TYPE_DELETE);
						req.setcId(0);
						req.setdId(did);
						presenter.setDynamic(req);
					}
				})
				.setNegativeButton("取消", null)
				.create().show();

	}
}
