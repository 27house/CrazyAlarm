package com.sunn.xhui.crazyalarm.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sunn.xhui.crazyalarm.R;

import java.util.List;

public class PicSelectUtils {

/*	public static void openCamera(Fragment fragment, int requestCode) {
		PictureSelector.create(fragment)
				.openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
				.theme(R.style.picture_white_style)
				.selectionMode(PictureConfig.SINGLE)// 多选 or 单选
				.previewImage(false)// 是否可预览图片
				.isCamera(false)// 是否显示拍照按钮
				.enableCrop(true)// 是否裁剪 true or false
				.freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
				.compress(true)// 是否压缩
				.compressSavePath(FileUtils.getChatImagePath(fragment.getActivity()))
				.previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
				.forResult(requestCode);//结果回调onActivityResult code
	}*/

/*	public static void selectPic(Fragment fragment, int requestCode) {
		// 进入相册 以下是例子：用不到的api可以不写
		PictureSelector.create(fragment)
				.openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
				.theme(R.style.picture_white_style)
				.imageSpanCount(4)// 每行显示个数 int
				.selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
				.previewImage(true)// 是否可预览图片 true or false
				.isCamera(true)// 是否显示拍照按钮 true or false
				.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
				.sizeMultiplier(1f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
				.enableCrop(true)// 是否裁剪 true or false
				.freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
				.compress(true)// 是否压缩 true or false
				.compressSavePath(FileUtils.getChatImagePath(fragment.getActivity()))
				.hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
				.isGif(true)// 是否显示gif图片 true or false
				.previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
				.forResult(requestCode);//结果回调onActivityResult code
	}*/

/*	public static void selectVideo(Fragment fragment, int requestCode) {
		PictureSelector.create(fragment)
				.openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
				.theme(R.style.picture_white_style)
				.imageSpanCount(4)// 每行显示个数 int
				.selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
				.previewVideo(true)// 是否可预览视频 true or false
				.isCamera(true)// 是否显示拍照按钮 true or false
				.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
				.sizeMultiplier(1f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
				.hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
				.openClickSound(false)// 是否开启点击声音 true or false
				.synOrAsy(true)//同步true或异步false 压缩 默认同步
				.setOutputCameraPath(FileUtils.getChatVideoPath(fragment.getActivity()))
				.videoQuality(1)// 视频录制质量 0 or 1 int
				.recordVideoSecond(60)//视频秒数录制 默认60s int
				.forResult(requestCode);//结果回调onActivityResult code
	}*/

/*	public static void openCameraForHeader(Activity activity) {
		PictureSelector.create(activity)
				.openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
				.theme(R.style.picture_white_style)
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
	}*/

/*	public static void selectPicForHeader(Activity activity) {
		PictureSelector.create(activity)
				.openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
				.theme(R.style.picture_white_style)
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
	}*/

	public static void selectPicForFeedback(Activity activity, List<LocalMedia> selectList) {
		PictureSelector.create(activity)
				.openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//				.theme(R.style.picture_white_style)
				.imageSpanCount(4)// 每行显示个数 int
				.selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
				.previewImage(true)// 是否可预览图片 true or false
				.isCamera(true)// 是否显示拍照按钮 true or false
				.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
				.sizeMultiplier(1f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
				.enableCrop(false)// 是否裁剪 true or false
				.freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
				.compress(true)// 是否压缩 true or false
				.compressSavePath(FileUtils.getFilesPath(activity, "dynamic"))
				.hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
				.isGif(false)// 是否显示gif图片 true or false
				.previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
				.selectionMedia(selectList)// 是否传入已选图片
				.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
	}
}
