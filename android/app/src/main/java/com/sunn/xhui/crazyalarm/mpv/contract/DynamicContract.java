package com.sunn.xhui.crazyalarm.mpv.contract;

import com.sunn.xhui.crazyalarm.data.Dynamic;
import com.sunn.xhui.crazyalarm.net.resp.DynamicListResp;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

public interface DynamicContract {
	interface Model {
		Observable<DynamicListResp> getDynamicList(int page, int page_count);
	}

	interface View {
		void showTip(String tip);

		void dismissLoad();
	}

	interface ListView extends View {
		void returnDynamicList(List<Dynamic> list);
	}

	interface Presenter {
		void getDynamicList(int page, int page_count);
	}
}