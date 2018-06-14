package com.sunn.xhui.crazyalarm.mpv.contract;

import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.LoginResp;

import rx.Observable;

/**
 * @author XHui.sun
 * created at 2018/5/29 0029  11:21
 */

public interface LoginContract {
	interface Model {
		/**
		 * 请求登录
		 *
		 * @param account 账号
		 * @param pwd     密码
		 * @return LoginResp
		 */
		Observable<LoginResp> login(String account, String pwd);
		/**
		 * 请求注册
		 *
		 * @param account 账号
		 * @param pwd     密码
		 * @return LoginResp
		 */
		Observable<BaseResp> register(String account, String pwd);
	}

	interface View {
		/**
		 * 登录结果回调
		 *
		 * @param success 是否成功
		 * @param tip     结果提示
		 */
		void loginResult(boolean success, String tip);
		/**
		 * 注册结果回调
		 *
		 * @param success 是否成功
		 * @param tip     结果提示
		 */
		void registerResult(boolean success, String tip);
	}

	interface Presenter {
		/**
		 * 登录
		 *
		 * @param account 账号
		 * @param pwd     密码
		 */
		void login(String account, String pwd) throws Exception;
		/**
		 * 注册
		 *
		 * @param account 账号
		 * @param pwd     密码
		 */
		void register(String account, String pwd);
	}
}
