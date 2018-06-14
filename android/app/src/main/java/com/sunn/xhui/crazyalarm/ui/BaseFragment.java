package com.sunn.xhui.crazyalarm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author XHui.sun
 * created at 2018/5/16 0016  9:47
 */

public abstract class BaseFragment extends Fragment {
	protected AppCompatActivity activity;

	Unbinder unbinder;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (AppCompatActivity) getActivity();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(initLayoutId(), container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activityCreated();
	}

	/**
	 * 初始化onCreateView
	 *
	 * @return 布局id
	 */
	protected abstract int initLayoutId();

	/**
	 * 初始化onActivityCreated
	 */
	protected abstract void activityCreated();

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
