package com.sunn.xhui.crazyalarm.mpv.presenter;

import com.sunn.xhui.crazyalarm.mpv.contract.UserContract;
import com.sunn.xhui.crazyalarm.mpv.model.UserModel;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.LoginResp;

import rx.Subscriber;

public class UserPresenter extends BasePresenter implements UserContract.Presenter {
	private UserContract.UserView userView;
	private UserContract.View view;
	private UserContract.Model model;

	public UserPresenter(UserContract.View view) {
		this.view = view;
		model = new UserModel();

	}

	public UserPresenter(UserContract.UserView userView) {
		this.userView = userView;
		model = new UserModel();
	}

	@Override
	public void updateAvatar(String path) {
		addSubscription(model.updateAvatar(path).subscribe(new Subscriber<BaseResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				view.updateAvatarRs(false, "网络异常！");
			}

			@Override
			public void onNext(BaseResp baseResp) {
				view.updateAvatarRs(baseResp.getResult() == 0, baseResp.getMessage());
			}
		}));
	}

	@Override
	public void updateInfo(final int type, final String value) {
		addSubscription(model.updateInfo(type, value).subscribe(new Subscriber<BaseResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				view.updateRs(type, false, "网络异常！");
			}

			@Override
			public void onNext(BaseResp baseResp) {
				view.updateRs(type, baseResp.getResult() == 0, baseResp.getMessage());
			}
		}));
	}

	@Override
	public void getUserInfo() {
		addSubscription(model.getUserInfo().subscribe(new Subscriber<LoginResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
			}

			@Override
			public void onNext(LoginResp loginResp) {
				if (userView != null) {
					userView.returnUserInfo(loginResp.getData());
				}
				if (view != null) {
					view.returnUserInfo(loginResp.getData());
				}
			}
		}));
	}
}
