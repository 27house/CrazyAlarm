package com.sunn.xhui.crazyalarm.mpv.model;

import com.sunn.xhui.crazyalarm.mpv.contract.DynamicContract;
import com.sunn.xhui.crazyalarm.net.RetrofitServiceManager;
import com.sunn.xhui.crazyalarm.net.resp.DynamicListResp;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DynamicModel implements DynamicContract.Model {
	@Override
	public Observable<DynamicListResp> getDynamicList(int page, int page_count) {
		return RetrofitServiceManager.getInstance().getService().getDynamicList(page, page_count)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
