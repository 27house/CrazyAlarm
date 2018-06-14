package com.sunn.xhui.crazyalarm.mpv.model;

import com.sunn.xhui.crazyalarm.mpv.contract.LoginContract;
import com.sunn.xhui.crazyalarm.net.RetrofitServiceManager;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.LoginResp;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author XHui.sun
 * created at 2018/5/29 0029  11:26
 */

public class LoginModel implements LoginContract.Model {
	@Override
	public Observable<LoginResp> login(String account, String pwd) {
		return RetrofitServiceManager.getInstance().getService().login(account, pwd)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<BaseResp> register(String account, String pwd) {
		return RetrofitServiceManager.getInstance().getService().register(account, pwd)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
