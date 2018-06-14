package com.sunn.xhui.crazyalarm.mpv.model;

import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.mpv.contract.UserContract;
import com.sunn.xhui.crazyalarm.net.RetrofitServiceManager;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.LoginResp;
import com.sunn.xhui.crazyalarm.utils.MyTools;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserModel implements UserContract.Model {
	@Override
	public Observable<BaseResp> updateAvatar(String path) {

		File file = new File(path);
		RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
		MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
		RequestBody accountBody = RequestBody.create(MediaType.parse("text/plain"), AlarmApp.userInfo.getAccount());

		return RetrofitServiceManager.getInstance().getService().uploadAvatar(part, accountBody)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io());
	}

	@Override
	public Observable<BaseResp> updateInfo(int type, String value) {
		return RetrofitServiceManager.getInstance().getService().updateInfo(type, value, AlarmApp.userInfo.getAccount())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io());
	}

	@Override
	public Observable<LoginResp> getUserInfo() {
		return RetrofitServiceManager.getInstance().getService().getUserInfo(AlarmApp.getAccount())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io());
	}
}
