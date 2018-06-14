package com.sunn.xhui.crazyalarm.mpv.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author XHui.sun
 * created at 2018/5/29 0029  11:27
 */

public class BasePresenter implements IBasePresenter {

	private CompositeSubscription compositeSubscription = new CompositeSubscription();

	@Override
	public void addSubscription(Subscription subscription) {
		if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
			compositeSubscription = new CompositeSubscription();
		}
		compositeSubscription.add(subscription);
	}

	@Override
	public void unSubscribe() {
		if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
			if (!compositeSubscription.isUnsubscribed()) {
				compositeSubscription.unsubscribe();
				compositeSubscription.clear();
				compositeSubscription = null;
			}
		}
	}
}