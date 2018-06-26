package com.sunn.xhui.crazyalarm.ui.alarm;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.AlarmGame;
import com.sunn.xhui.crazyalarm.event.JsInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * h5小游戏
 *
 * @author XHui.sun
 * created at 2018/5/24 0024  14:21
 */
public class WebGameActivity extends AppCompatActivity {
	public static final String EXTRA_TASK = "EXTRA_TASK";

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.webView)
	WebView webView;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_web_game);
		ButterKnife.bind(this);
		appBar.setVisibility(View.GONE);
		setWebSetting();
		AlarmGame alarmGame = (AlarmGame) getIntent().getSerializableExtra(EXTRA_TASK);
		if (alarmGame == null) {
			finish();
			return;
		}
		int score = alarmGame.getScore();
		url = alarmGame.getH_url() + "?score=" + score;
		webView.loadUrl(url);
	}

	private void setWebSetting() {
		//声明WebSettings子类
		WebSettings webSettings = webView.getSettings();

		//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
		webSettings.setJavaScriptEnabled(true);
		// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
		// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

		//支持插件
//		webSettings.setPluginsEnabled(true);

		//设置自适应屏幕，两者合用
		webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
		webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

		//缩放操作
		webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
		webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
		webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

		//其他细节操作
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
		webSettings.setAllowFileAccess(true); //设置可以访问文件
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
		webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
		webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
		webView.addJavascriptInterface(new JsInterface(webView, this, url), "android");
	}

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
			} else if (gameSuccess) {
				finish();
			} else {
				Toast.makeText(this, "请先完成游戏任务！", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	public boolean gameSuccess;
}
