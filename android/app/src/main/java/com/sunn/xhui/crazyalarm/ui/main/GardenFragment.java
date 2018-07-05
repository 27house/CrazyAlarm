package com.sunn.xhui.crazyalarm.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.UserInfo;
import com.sunn.xhui.crazyalarm.mpv.contract.UserContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.UserPresenter;
import com.sunn.xhui.crazyalarm.ui.alarm.WebGameActivity;
import com.sunn.xhui.crazyalarm.ui.garden.EditInfoActivity;
import com.sunn.xhui.crazyalarm.ui.garden.LoginActivity;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.ui.garden.SettingsActivity;
import com.sunn.xhui.crazyalarm.utils.FrescoBuilder;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 家园-个人中心
 *
 * @author XHui.sun
 * created at 2018/5/24 0024  11:49
 */
public class GardenFragment extends BaseFragment implements UserContract.UserView {

	@BindView(R.id.rl_header_bg)
	View rlHeaderBg;
	@BindView(R.id.iv_header)
	SimpleDraweeView ivHeader;
	@BindView(R.id.tv_nickname)
	TextView tvNickname;
	@BindView(R.id.tv_desc)
	TextView tvDesc;
	@BindView(R.id.ll_clear_cache)
	TextView llClearCache;
	@BindView(R.id.btn_exit)
	Button btnExit;

	private UserPresenter presenter;

	@Override
	protected int initLayoutId() {
		return R.layout.fragment_garden;
	}

	@Override
	protected void activityCreated() {
		presenter = new UserPresenter(this);
		presenter.getUserInfo();
	}

	@Override
	public void onResume() {
		super.onResume();
		fillView();
	}

	private void fillView() {
		String avatar = "";
		if (AlarmApp.userInfo != null) {
			avatar = AlarmApp.userInfo.getAvatar();
			tvNickname.setText(AlarmApp.userInfo.getNickname());
			Drawable drawable = getSexDrawable();
			if (drawable != null) {
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				tvNickname.setCompoundDrawables(null, null, drawable, null);
			} else {
				tvNickname.setCompoundDrawables(null, null, null, null);
			}
			tvDesc.setText(AlarmApp.userInfo.getMotto());
		} else {
			tvNickname.setText("");
			tvNickname.setCompoundDrawables(null, null, null, null);
			tvDesc.setText("");
		}
		FrescoBuilder.Start(activity, ivHeader, avatar)
				.setIsCircle(true)
				.setFailureImage(ContextCompat.getDrawable(activity, R.mipmap.ic_header))
				.build();
	}

	private Drawable getSexDrawable() {
		switch (AlarmApp.userInfo.getSex()) {
			case "0":
				return null;
			case "1":
				return ContextCompat.getDrawable(activity, R.mipmap.ic_male);
			case "2":
				return ContextCompat.getDrawable(activity, R.mipmap.ic_female);
			default:
				return null;
		}
	}

	@OnClick(R.id.iv_header)
	public void clickEdit(View view) {
		if (TextUtils.isEmpty(AlarmApp.getAccount())) {
			startActivity(new Intent(activity, LoginActivity.class));
		} else {
			startActivity(new Intent(activity, EditInfoActivity.class));
		}
	}

	@OnClick(R.id.ll_clear_cache)
	public void clickClearCache(View view) {
		new AlertDialog.Builder(activity)
				.setMessage("确定要清除app缓存吗？")
				.setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create().show();
	}

	@OnClick(R.id.ll_integral)
	public void clickIntegral(View view) {
		Intent intent = new Intent(activity, WebAppActivity.class);
		String url ="http://192.168.0.136:8080/crazy/htmlapp/IntegralMall.html?account=" + AlarmApp.getAccount();
		intent.putExtra(WebAppActivity.EXTRA_URL, url);
		startActivity(intent);
	}

	@OnClick(R.id.ll_setting)
	public void clickSetting(View view) {
		startActivity(new Intent(activity, SettingsActivity.class));
	}


	@OnClick(R.id.btn_exit)
	public void clickExit(View view) {
		AlarmApp.setAccount(null);
		fillView();
	}

	@Override
	public void returnUserInfo(UserInfo info) {
		AlarmApp.setAccount(info);
		fillView();
	}

}
