package com.sunn.xhui.crazyalarm.ui.main;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.utils.FileUtils;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * h5小游戏
 *
 * @author XHui.sun
 * created at 2018/5/24 0024  14:21
 */
public class WebAppActivity extends AppCompatActivity {
	public static final String EXTRA_URL = "EXTRA_URL";

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.webView)
	WebView webView;
	String url;
	@BindView(R.id.pb_web_view)
	ProgressBar pbWebView;
	private ProgressHandler handler;
	private WebSettings webSettings;
	int progress;
	int newProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_game);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		toolbar.setTitle("正在加载...");
		setWebSetting();
		handler = new ProgressHandler(this);
		pbWebView.setMax(100);
		url = getIntent().getStringExtra(EXTRA_URL);
		webView.loadUrl(url);
	}

	private void setWebSetting() {
		// 设置WebView相关属性
		webSettings = webView.getSettings();
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
		webSettings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
		webSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
		webSettings.setAppCacheEnabled(true);//是否使用缓存
		webSettings.setAppCachePath(FileUtils.getCachePath(this));// 缓存路径
		webSettings.setDomStorageEnabled(true);//设置是否启用了DOM storage AP搜索
		// 排版适应屏幕
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

		webView.requestFocus();// 使页面获取焦点，防止点击无响应
		// 隐藏滚动条
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		//其他细节操作
		webSettings.setAllowFileAccess(true); //设置可以访问文件
		webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
		webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//        webSettings.setPluginsEnabled(true); //支持插件
//        if (Build.VERSION.SDK_INT >= 19) {
//            //关闭单个view的硬件加速, 关闭后无法播放视频
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
		if (Build.VERSION.SDK_INT >= 17) {
			// WebView是否需要用户的手势进行媒体播放，默认值为true
			webSettings.setMediaPlaybackRequiresUserGesture(false);
		}

		webView.setWebViewClient(new JLongWebViewClient());
		// 设置setWebChromeClient对象
		webView.setWebChromeClient(new JLongWebChromeClient());
	}

	private void startHandler() {
		progress = 0;
		handler.removeMessages(3);
		handler.sendEmptyMessage(3);
	}//https://www.91kuaisheng.com/app/user/tixian.php

	@Override
	protected void onResume() {
		super.onResume();
		webView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		webView.onPause();
	}

	@Override
	protected void onDestroy() {
		if (webView != null) {
			webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			webView.clearHistory();

			((ViewGroup) webView.getParent()).removeView(webView);
			webView.destroy();
			webView = null;
		}

		super.onDestroy();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				return super.onKeyUp(keyCode, event);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private class JLongWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			LogUtil.e("--url--" + url);
			LogUtil.e("--webView.getUrl()--" + webView.getUrl());
			view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			//重点：看下面
			WebView.HitTestResult hitTestResult = view.getHitTestResult();
			//hitTestResult==null解决重定向问题
			if (!TextUtils.isEmpty(url) && hitTestResult == null) {
				view.loadUrl(url);
				return true;
			}
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			LogUtil.e("--onPageStartedUrl--" + url);
			startHandler();
		}
	}

	private class JLongWebChromeClient extends WebChromeClient {
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			if (title != null && !title.isEmpty()) {
				toolbar.setTitle(title);
			}
		}

		@Override
		public void onProgressChanged(WebView view, int progress) {
			super.onProgressChanged(view, progress);
			newProgress = progress;
		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
			new AlertDialog.Builder(WebAppActivity.this)
					.setTitle("JsAlert")
					.setMessage(message)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					})
					.setCancelable(false)
					.show();
			return true;
		}
	}

	/**
	 * 防止内存泄露
	 *
	 * @author XHui.sun
	 * created at 2017/10/26 0026  17:01
	 */
	private static class ProgressHandler extends Handler {
		WeakReference<WebAppActivity> mActivity;

		ProgressHandler(WebAppActivity activity) {
			mActivity = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				if (mActivity == null) {
					return;
				}
				mActivity.get().handleMessage(msg);
			} catch (Exception e) {
				LogUtil.e(e.getMessage());
			}
		}
	}

	private void handleMessage(Message msg) throws Exception {
		if (pbWebView == null) {
			return;
		}
		switch (msg.what) {
			case 0:
				pbWebView.setProgress(newProgress);
				pbWebView.setVisibility(View.INVISIBLE);
				handler.removeMessages(3);
				break;
			case 1:
				if (pbWebView.getVisibility() == View.INVISIBLE) {
					pbWebView.setVisibility(View.VISIBLE);
				}
				progress = progress + 10;
				if (newProgress < 100 && progress >= 80) {
					progress = 80;
				}
				pbWebView.setProgress(progress);
				break;
			case 3:
				if (progress >= 100 && newProgress >= 100) {
					handler.sendEmptyMessage(0);
				} else {
					handler.sendEmptyMessage(1);
				}
				handler.sendEmptyMessageDelayed(3, 50);
				break;
			default:
				break;
		}
	}
}
