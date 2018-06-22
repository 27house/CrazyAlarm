package com.sunn.xhui.crazyalarm.ui.community;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.SelectImg;
import com.sunn.xhui.crazyalarm.event.RefreshAlarmListEvent;
import com.sunn.xhui.crazyalarm.event.RefreshDynamicListEvent;
import com.sunn.xhui.crazyalarm.mpv.contract.DynamicContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.DynamicPresenter;
import com.sunn.xhui.crazyalarm.net.req.AddDynamicReq;
import com.sunn.xhui.crazyalarm.ui.adapter.AddImageGridAdapter;
import com.sunn.xhui.crazyalarm.ui.widget.list.SpacesItemDecoration;
import com.sunn.xhui.crazyalarm.utils.DensityUtil;
import com.sunn.xhui.crazyalarm.utils.PicSelectUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author XHui.sun
 * created at 2018/6/15 0015  15:40
 */

public class AddDynamicActivity extends AppCompatActivity implements AddImageGridAdapter.ClickCallback, DynamicContract.AddView {

	@BindView(R.id.tv_cancel)
	TextView tvCancel;
	@BindView(R.id.tv_send)
	TextView tvSend;
	@BindView(R.id.et_content)
	EditText etContent;
	@BindView(R.id.rv_pic)
	RecyclerView rvPic;
	private AddImageGridAdapter gridAdapter;
	private DynamicPresenter presenter;

	private List<SelectImg> imgList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dynamic);
		ButterKnife.bind(this);
		presenter = new DynamicPresenter(this);
		gridAdapter = new AddImageGridAdapter(this, this);
		gridAdapter.setLoadMore(false);
		gridAdapter.showFooterV(false);
		GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
		rvPic.setLayoutManager(gridLayoutManager);
		//添加ItemDecoration，item之间的间隔
		int leftRight = DensityUtil.dip2px(this, 8);
		int topBottom = DensityUtil.dip2px(this, 8);
		rvPic.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
		rvPic.setAdapter(gridAdapter);
	}

	@OnClick(R.id.tv_cancel)
	public void clickCancel(View view) {
		finishActivity();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finishActivity();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	private void finishActivity() {
		new AlertDialog.Builder(this)
				.setTitle("提示")
				.setMessage("退出后不会保存，是否确定退出？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNegativeButton("取消", null).create().show();
	}

	@OnClick(R.id.tv_send)
	public void clickSend(View view) {
		AddDynamicReq req = new AddDynamicReq();
		req.setContent(etContent.getText().toString());
		List<String> picPaths = new ArrayList<>();
		for (LocalMedia media : selectList) {
			picPaths.add(media.getPath());
		}
		req.setPicPaths(picPaths);
		presenter.addDynamic(req);
	}

	private List<LocalMedia> selectList = new ArrayList<>();

	@Override
	public void addPic() {
		PicSelectUtils.selectPicForFeedback(this, selectList);
	}

	@Override
	public void removeItem(SelectImg selectImg) {
		removeLocalMedia(selectImg);
	}

	private void removeLocalMedia(SelectImg img) {
		for (LocalMedia media : selectList) {
			if (media.getPath().equals(img.getLocalPath())) {
				selectList.remove(media);
				break;
			}
		}
	}

	@Override
	public void uploadItem(SelectImg selectImg) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		if (resultCode == RESULT_CANCELED) {
			return;
		}
		if (requestCode == PictureConfig.CHOOSE_REQUEST) {
			Observable.just(data)
					.flatMap(new Func1<Intent, Observable<LocalMedia>>() {
						@Override
						public Observable<LocalMedia> call(Intent intent) {
							imgList.clear();
							selectList = PictureSelector.obtainMultipleResult(data);
							return Observable.from(selectList);
						}
					})
					.map(new Func1<LocalMedia, SelectImg>() {
						@Override
						public SelectImg call(LocalMedia media) {
							SelectImg feedbackImg = new SelectImg();
							feedbackImg.setLocalPath(media.getCompressPath());
							Bitmap bitmap = BitmapFactory.decodeFile(media.getCompressPath());
							feedbackImg.setThumb(bitmap);
							return feedbackImg;
						}
					})
					// 指定 subscribe() 发生在 IO 线程
					.subscribeOn(Schedulers.io())
					// 指定 Subscriber 的回调发生在主线程
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<SelectImg>() {
						@Override
						public void onCompleted() {
							gridAdapter.setImgList(imgList);
						}

						@Override
						public void onError(Throwable e) {

						}

						@Override
						public void onNext(SelectImg feedbackImg) {
							imgList.add(feedbackImg);
						}
					});
		}
	}

	@Override
	public void showTip(String tip) {
		Snackbar.make(tvCancel, tip, Snackbar.LENGTH_SHORT).show();
	}

	@Override
	public void dismissLoad() {

	}

	@Override
	public void returnResult(boolean success) {
		if (success) {
			EventBus.getDefault().post(new RefreshDynamicListEvent());
			finish();
		}
	}
}
