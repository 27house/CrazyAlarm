package com.sunn.xhui.crazyalarm.event;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.sunn.xhui.crazyalarm.ui.alarm.WebGameActivity;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

public class JsInterface {
	private WebView webView;
	private WebGameActivity activity;
	private String url;

	public JsInterface(WebView webView, WebGameActivity activity, String url) {
		this.webView = webView;
		this.activity = activity;
		this.url = url;
	}

	@JavascriptInterface
	public void returnResult(boolean success) {
		activity.gameSuccess = success;
		LogUtil.e("---success---" + success);
	}

	@JavascriptInterface
	public void finishGame() {
		activity.finish();
	}

	@JavascriptInterface
	public void showFinishAlert() {
		new AlertDialog.Builder(activity)
				.setTitle("提示")
				.setMessage("已完成任务")
				.setPositiveButton("结束响铃", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								activity.finish();
							}
						});
					}
				})
				.setNegativeButton("再玩一局", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								webView.loadUrl(url);
							}
						});
					}
				})
				.create().show();
	}
}
