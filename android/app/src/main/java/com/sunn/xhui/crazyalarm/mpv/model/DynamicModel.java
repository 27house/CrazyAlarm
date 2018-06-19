package com.sunn.xhui.crazyalarm.mpv.model;

import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.mpv.contract.DynamicContract;
import com.sunn.xhui.crazyalarm.net.RetrofitServiceManager;
import com.sunn.xhui.crazyalarm.net.req.AddDynamicReq;
import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.DynamicListResp;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DynamicModel implements DynamicContract.Model {
	@Override
	public Observable<DynamicListResp> getDynamicList(int page, int page_count) {
		return RetrofitServiceManager.getInstance().getService().getDynamicList(page, page_count)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	@Override
	public Observable<BaseResp> addDynamic(AddDynamicReq req) {
		List<File> files = new ArrayList<>();
		for (String path : req.getPicPaths()) {
			if (path.endsWith(".jpg") || path.endsWith(".png") || path.endsWith(".jpeg")) {
				files.add(new File(path));
			}
		}
		List<MultipartBody.Part> parts = new ArrayList<>(files.size());
		for (File file : files) {
			RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
			MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
			parts.add(part);
		}
		RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), "add_dynamic");
		RequestBody accountBody = RequestBody.create(MediaType.parse("text/plain"), AlarmApp.userInfo.getAccount());
		RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), req.getContent());
		return RetrofitServiceManager.getInstance().getService().addDynamic(parts, typeBody, accountBody, contentBody)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
