package com.sunn.xhui.crazyalarm.mpv.contract;

import okhttp3.ResponseBody;
import rx.Observable;

public interface DownloadContract {
	interface Model {
		Observable<ResponseBody> downloadFile(String url);

		Observable<Boolean> saveFile(ResponseBody responseBody, String filePathName);
	}

	interface View {
		void downloadSuccess();

		void downloadProcess(int progress);

		void downloadFailed(String error);

		String filePathName();
	}

	interface Presenter {
		void downloadFile(String url);

		void saveFile(ResponseBody responseBody, String filePathName);
	}
}
