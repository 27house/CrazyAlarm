package com.sunn.xhui.crazyalarm.mpv.model;

import com.sunn.xhui.crazyalarm.mpv.contract.DownloadContract;
import com.sunn.xhui.crazyalarm.net.RetrofitServiceManager;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DownloadModel implements DownloadContract.Model {
    @Override
    public Observable<ResponseBody> downloadFile(String url) {
        return RetrofitServiceManager.getInstance().getService().downloadFile(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Boolean> saveFile(final ResponseBody responseBody, final String filePathName) {
        Observable.OnSubscribe<Boolean> observable = new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                //异步操作相关代码
                dealBody(subscriber, responseBody, filePathName);
            }
        };
        return Observable.create(observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void dealBody(Subscriber<? super Boolean> subscriber, final ResponseBody responseBody, final String filePathName) {
        try {
            File futureStudioIconFile = new File(filePathName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = responseBody.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = responseBody.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    LogUtil.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                subscriber.onNext(true);
            } catch (IOException e) {
                subscriber.onNext(false);
                LogUtil.e(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            subscriber.onNext(false);
            LogUtil.e(e.getMessage());
        }
    }
}
