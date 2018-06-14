package com.sunn.xhui.crazyalarm.mpv.model;

import com.sunn.xhui.crazyalarm.mpv.contract.AlarmContract;
import com.sunn.xhui.crazyalarm.net.RetrofitServiceManager;
import com.sunn.xhui.crazyalarm.net.resp.TaskListResp;
import com.sunn.xhui.crazyalarm.net.resp.VoiceListResp;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AlarmModel implements AlarmContract.Model {

	@Override
	public Observable<VoiceListResp> getRingtoneList() {
		return RetrofitServiceManager.getInstance().getService()
				.ringtoneList()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<TaskListResp> taskList() {
		return RetrofitServiceManager.getInstance().getService()
				.taskList()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
