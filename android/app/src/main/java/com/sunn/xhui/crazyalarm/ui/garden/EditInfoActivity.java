package com.sunn.xhui.crazyalarm.ui.garden;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.UserInfo;
import com.sunn.xhui.crazyalarm.mpv.contract.UserContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.UserPresenter;
import com.sunn.xhui.crazyalarm.utils.FrescoBuilder;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class EditInfoActivity extends AppCompatActivity implements UserContract.View {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.iv_header)
	SimpleDraweeView ivHeader;
	@BindView(R.id.tv_decs)
	TextView tvDecs;
	@BindView(R.id.tv_account)
	TextView tvAccount;
	@BindView(R.id.tv_nickname)
	TextView tvNickname;
	@BindView(R.id.tv_sex)
	TextView tvSex;
	private UserPresenter presenter;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_info);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		toolbar.setTitle("个人信息");
		presenter = new UserPresenter(this);
		presenter.getUserInfo();
		fillView();
	}

	@OnClick(R.id.iv_header)
	public void clickHeader(View view) {
		PictureSelector.create(this)
				.openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//				.theme(R.style.picture_white_style)
				.imageSpanCount(4)// 每行显示个数 int
				.selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
				.previewImage(true)// 是否可预览图片 true or false
				.isCamera(true)// 是否显示拍照按钮 true or false
				.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
				.sizeMultiplier(0.8f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
				.enableCrop(true)// 是否裁剪 true or false
				.compress(true)// 是否压缩 true or false
				.withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
				.hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
				.isGif(false)// 是否显示gif图片 true or false
				.freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
				.circleDimmedLayer(true)// 是否圆形裁剪 true or false
				.showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
				.showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
				.openClickSound(true)// 是否开启点击声音 true or false
				.rotateEnabled(false) // 裁剪是否可旋转图片 true or false
				.scaleEnabled(false)// 裁剪是否可放大缩小图片 true or false
				.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
	}

	@OnClick(R.id.tv_decs)
	public void clickDecs(View view) {
		final EditText et = new EditText(this);
		new AlertDialog.Builder(this)
				.setMessage("请输入签名：")
				.setView(et)
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String input = et.getText().toString();
						presenter.updateInfo(3, input);
					}
				})
				.setNegativeButton("取消", null)
				.create().show();
	}

	@OnClick(R.id.tv_nickname)
	public void clickNickname(View view) {
		final EditText et = new EditText(this);
		new AlertDialog.Builder(this)
				.setMessage("请输入昵称：")
				.setView(et)
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String input = et.getText().toString();
						if (TextUtils.isEmpty(input)) {
							Snackbar.make(tvNickname, "昵称不能为空！" + input, Snackbar.LENGTH_SHORT).show();
						} else {
							presenter.updateInfo(0, input);
						}
					}
				})
				.setNegativeButton("取消", null)
				.create().show();
	}

	@OnClick(R.id.tv_sex)
	public void clickSex(View view) {
		String[] cities = {"保密", "男", "女"};
		new AlertDialog.Builder(this)
				.setTitle("请选择性别：")
				.setSingleChoiceItems(cities, Integer.parseInt(AlarmApp.userInfo.getSex()), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						presenter.updateInfo(1, String.valueOf(which));
						tvSex.setText(getSexStr(which));
					}
				})
				.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create().show();
	}

	private String getSexStr(int which) {
		switch (which) {
			case 0:
				return "性别：保密";
			case 1:
				return "性别：男";
			case 2:
				return "性别：女";
			default:
				return "性别：保密";
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CANCELED) {
			return;
		}
		if (requestCode == PictureConfig.CHOOSE_REQUEST) {
			Observable.just(data)
					.map(new Func1<Intent, String>() {
						@Override
						public String call(Intent intent) {
							return PictureSelector.obtainMultipleResult(intent).get(0).getCompressPath();
						}
					})
					// 指定 subscribe() 发生在 IO 线程
					.subscribeOn(Schedulers.io())
					// 指定 Subscriber 的回调发生在主线程
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<String>() {
						@Override
						public void onCompleted() {

						}

						@Override
						public void onError(Throwable e) {

						}

						@Override
						public void onNext(String path) {
							LogUtil.e("---imgPath---\n" + path);
							// 调用修改头像接口
							presenter.updateAvatar(path);
							FrescoBuilder.Start(EditInfoActivity.this, ivHeader, path)
									.setIsCircle(true)
									.build();
						}
					});
		}
	}

	@Override
	public void updateAvatarRs(boolean success, String tip) {
		Snackbar.make(ivHeader, tip, Snackbar.LENGTH_SHORT).show();
		presenter.getUserInfo();
	}

	@Override
	public void updateRs(int type, boolean success, String tip) {
		Snackbar.make(ivHeader, tip, Snackbar.LENGTH_SHORT).show();
		presenter.getUserInfo();
	}

	@Override
	public void returnUserInfo(UserInfo info) {
		AlarmApp.setAccount(info);
		fillView();
	}

	@SuppressLint("SetTextI18n")
	private void fillView() {
		if (AlarmApp.userInfo == null) {
			return;
		}
		tvDecs.setText(AlarmApp.userInfo.getMotto());
		tvAccount.setText("账号：" + AlarmApp.userInfo.getAccount());
		tvNickname.setText("昵称：" + AlarmApp.userInfo.getNickname());
		tvSex.setText(getSexStr(Integer.parseInt(AlarmApp.userInfo.getSex())));
		LogUtil.e("--Avatar--" + AlarmApp.userInfo.getAvatar());
		FrescoBuilder.Start(this, ivHeader, AlarmApp.userInfo.getAvatar())
				.setIsCircle(true)
				.setFailureImage(ContextCompat.getDrawable(this, R.mipmap.ic_header))
				.build();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
