package com.sunn.xhui.crazyalarm.mpv.contract;

import com.sunn.xhui.crazyalarm.data.UserInfo;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.LoginResp;

import rx.Observable;

/**
 * @author XHui.sun
 * created at 2018/5/31 0031  15:31
 */
public interface UserContract {
	interface Model {
		/**
		 * 请求上传头像
		 *
		 * @param path 图片路径
		 * @return 上传结果
		 */
		Observable<BaseResp> updateAvatar(String path);

		Observable<BaseResp> updateInfo(int type, String value);

		Observable<LoginResp> getUserInfo();
	}


	interface UserView{
		void returnUserInfo(UserInfo info);
	}

	interface View extends UserView {
		/**
		 * 上传结果回调
		 *
		 * @param success 是否成功
		 * @param tip     提示文案
		 */
		void updateAvatarRs(boolean success, String tip);

		void updateRs(int type, boolean success, String tip);
	}


	interface Presenter {
		/**
		 * 上传头像
		 *
		 * @param path 图片路径
		 */
		void updateAvatar(String path);

		void updateInfo(int type, String value);

		void getUserInfo();
	}
}
