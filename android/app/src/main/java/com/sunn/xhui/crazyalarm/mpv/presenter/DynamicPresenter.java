package com.sunn.xhui.crazyalarm.mpv.presenter;

import com.sunn.xhui.crazyalarm.mpv.contract.DynamicContract;
import com.sunn.xhui.crazyalarm.mpv.model.DynamicModel;
import com.sunn.xhui.crazyalarm.net.req.AddDynamicReq;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.DynamicListResp;

import rx.Subscriber;

public class DynamicPresenter extends BasePresenter implements DynamicContract.Presenter {
	DynamicContract.ListView listView;
	DynamicContract.AddView addView;
	DynamicContract.Model model;

	public DynamicPresenter(DynamicContract.ListView listView) {
		this.listView = listView;
		model = new DynamicModel();
	}

	public DynamicPresenter(DynamicContract.AddView addView) {
		this.addView = addView;
		model = new DynamicModel();
	}

	@Override
	public void getDynamicList(int page, int page_count) {
		addSubscription(model.getDynamicList(page, page_count).subscribe(new Subscriber<DynamicListResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				listView.dismissLoad();
				listView.showTip("网络异常！");
			}

			@Override
			public void onNext(DynamicListResp dynamicListResp) {
				listView.dismissLoad();
				listView.returnDynamicList(dynamicListResp.getList());
				if (dynamicListResp.getResult() != 0) {
					listView.showTip(dynamicListResp.getMessage());
				}
			}
		}));
	}

	@Override
	public void addDynamic(AddDynamicReq req) {
		addSubscription(model.addDynamic(req).subscribe(new Subscriber<BaseResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				addView.dismissLoad();
				addView.showTip("网络异常！");
			}

			@Override
			public void onNext(BaseResp baseResp) {
				addView.dismissLoad();
				addView.returnResult(baseResp.getResult() == 0);
				addView.showTip(baseResp.getMessage());
			}
		}));
	}
}
