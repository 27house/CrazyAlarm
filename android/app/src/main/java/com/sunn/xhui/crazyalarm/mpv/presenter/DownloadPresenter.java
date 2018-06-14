package com.sunn.xhui.crazyalarm.mpv.presenter;

import android.os.Looper;
import android.util.Log;

import com.sunn.xhui.crazyalarm.mpv.contract.DownloadContract;
import com.sunn.xhui.crazyalarm.mpv.model.DownloadModel;
import com.sunn.xhui.crazyalarm.net.progress.DownloadProgressHandler;
import com.sunn.xhui.crazyalarm.net.progress.ProgressHelper;

import okhttp3.ResponseBody;
import rx.Subscriber;

public class DownloadPresenter extends BasePresenter implements DownloadContract.Presenter {
    private DownloadContract.Model model;
    private DownloadContract.View view;

    public DownloadPresenter(DownloadContract.View view) {
        this.view = view;
        model = new DownloadModel();
    }

    @Override
    public void downloadFile(String url) {
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                Log.e("onProgress", String.format("%d%% done\n", (100 * bytesRead) / contentLength));
                Log.e("done", "--->" + String.valueOf(done));
                int progress = (int) ((100 * bytesRead) / contentLength);
                view.downloadProcess(progress);
            }
        });
        addSubscription(model.downloadFile(url).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.downloadFailed("网络异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                view.downloadProcess(100);
                saveFile(responseBody, view.filePathName());

            }
        }));
    }

    @Override
    public void saveFile(final ResponseBody responseBody, final String filePathName) {
        addSubscription(model.saveFile(responseBody, filePathName).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.downloadFailed("保存本地失败！");
            }

            @Override
            public void onNext(Boolean success) {
                if (success) {
                    view.downloadSuccess();
                } else {

                    view.downloadFailed("下载失败！");
                }
            }
        }));
    }
}
