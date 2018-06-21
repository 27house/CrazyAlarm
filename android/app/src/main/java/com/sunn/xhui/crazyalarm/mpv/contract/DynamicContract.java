package com.sunn.xhui.crazyalarm.mpv.contract;

import com.sunn.xhui.crazyalarm.data.Dynamic;
import com.sunn.xhui.crazyalarm.net.req.AddDynamicReq;
import com.sunn.xhui.crazyalarm.net.req.SetDynamicReq;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.DynamicListResp;

import java.util.List;

import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface DynamicContract {
	interface Model {
		Observable<DynamicListResp> getDynamicList(int page, int page_count);

		Observable<BaseResp> addDynamic(AddDynamicReq req);

		Observable<BaseResp> setDynamic(SetDynamicReq req);

	}

	interface View {
		void showTip(String tip);

		void dismissLoad();
	}

	interface ControlView extends View {
		void returnSetResult(boolean success, int type, String tip, int did);
	}

	interface ListView extends ControlView {
		void returnDynamicList(List<Dynamic> list);
	}

	interface AddView extends View {
		void returnResult(boolean success);
	}


	interface Presenter {
		void getDynamicList(int page, int page_count);

		void addDynamic(AddDynamicReq req);

		void setDynamic(SetDynamicReq req);


	}
}
