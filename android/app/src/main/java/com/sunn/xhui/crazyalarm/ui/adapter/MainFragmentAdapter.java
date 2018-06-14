package com.sunn.xhui.crazyalarm.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {
	//创建一个List<Fragment>
	private List<Fragment> listFragment = new ArrayList<>();

	// 定义构造带两个参数
	public MainFragmentAdapter(FragmentManager fm, List<Fragment> listFragment) {
		super(fm);
		this.listFragment = listFragment;
	}

	@Override
	public Fragment getItem(int pos) {
		return listFragment.get(pos); //返回第几个fragment
	}

	@Override
	public int getCount() {
		return listFragment.size(); //总共有多少个fragment
	}
}
