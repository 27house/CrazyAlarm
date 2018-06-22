package com.sunn.xhui.crazyalarm.ui.alarm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.sunn.xhui.crazyalarm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * h5小游戏
 *
 * @author XHui.sun
 * created at 2018/5/24 0024  14:21
 */
public class WebGameActivity extends AppCompatActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.webView)
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_web_game);
		ButterKnife.bind(this);
		appBar.setVisibility(View.GONE);
//		webView.loadUrl("file:///android_asset/color/index.html");
//		webView.loadUrl("file:///android_asset/mario/index.html");
//		webView.loadUrl("file:///android_asset/animal/index.html");
//		webView.loadUrl("file:///android_asset/remove/index.html");
		webView.loadUrl("file:///android_asset/fruit/index.html");
	}
}
