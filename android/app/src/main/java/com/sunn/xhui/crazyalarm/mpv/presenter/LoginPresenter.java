package com.sunn.xhui.crazyalarm.mpv.presenter;

import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.mpv.contract.LoginContract;
import com.sunn.xhui.crazyalarm.mpv.model.LoginModel;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.LoginResp;

import rx.Subscriber;

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {
	private LoginContract.Model model;
	private LoginContract.View view;

	public LoginPresenter(LoginContract.View view) {
		this.view = view;
		model = new LoginModel();
	}

	@Override
	public void login(String account, String pwd) throws Exception {
		addSubscription(model.login(account, pwd).subscribe(new Subscriber<LoginResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				view.loginResult(false, "网络异常！");
			}

			@Override
			public void onNext(LoginResp loginResp) {
				AlarmApp.setAccount(loginResp.getData());
				view.loginResult(loginResp.getResult() == 0, loginResp.getMessage());
			}
		}));
	}

	@Override
	public void register(String account, String pwd) {
		addSubscription(model.register(account, pwd).subscribe(new Subscriber<BaseResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				view.registerResult(false, "网络异常！");
			}

			@Override
			public void onNext(BaseResp loginResp) {
				view.registerResult(loginResp.getResult() == 0, loginResp.getMessage());
			}
		}));
	}
}
