package com.sunn.xhui.crazyalarm.mpv.presenter;

import rx.Subscription;

/**
 * @author XHui.sun
 * created at 2018/5/29 0029  11:27
 */

public interface IBasePresenter {

	/**
	 * 添加Subscription
	 * @param subscription Subscription
	 */
	void addSubscription(Subscription subscription);

	/**
	 * 解除
	 */
	void unSubscribe();
}
