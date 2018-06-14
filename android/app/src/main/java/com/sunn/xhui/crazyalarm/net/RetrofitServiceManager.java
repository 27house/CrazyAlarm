package com.sunn.xhui.crazyalarm.net;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.Constant;
import com.sunn.xhui.crazyalarm.net.api.AlarmService;
import com.sunn.xhui.crazyalarm.net.progress.ProgressHelper;
import com.sunn.xhui.crazyalarm.utils.LogUtil;
import com.sunn.xhui.crazyalarm.utils.NetUtil;
import com.sunn.xhui.crazyalarm.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author XHui.sun
 * created at 2018/5/29 0029  11:03
 */

public class RetrofitServiceManager implements HttpLoggingInterceptor.Logger, Interceptor {
	private AlarmService service;
	private OkHttpClient okHttpClient;

	private RetrofitServiceManager() {
		// 创建 OKHttpClient，这是Okhttp3的使用。
		HttpLoggingInterceptor mHttpLogInterceptor = new HttpLoggingInterceptor(this);
		mHttpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		Interceptor mUrlInterceptor = this;
		// 100Mb
		Cache cache = new Cache(new File(AlarmApp.HttpCachePath), 1024 * 1024 * 100);
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.connectTimeout(12, TimeUnit.SECONDS)
				.writeTimeout(20, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS)
				.retryOnConnectionFailure(true)
				.addInterceptor(mHttpLogInterceptor)
				.addInterceptor(mUrlInterceptor)
				.cache(cache)
				// HTTPS
				.sslSocketFactory(getDefaultSSLSocketFactory())
//                 Hostname domain.com not verified
				.hostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});


		builder = ProgressHelper.addProgress(builder);
		okHttpClient = builder.build();
		// 创建Retrofit
		Retrofit mRetrofit = new Retrofit.Builder()
				.baseUrl(Constant.IP.BASE_URL)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
						.setDateFormat("yyyy-MM-dd HH:mm:ss")
						.create()))
				.client(okHttpClient)
				.build();
		service = mRetrofit.create(AlarmService.class);
	}

	private static synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

						}

						@Override
						public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
						}

						@Override
						public X509Certificate[] getAcceptedIssuers() {
							return new X509Certificate[0];
						}
					}
			}, null);
			return sslContext.getSocketFactory();
		} catch (GeneralSecurityException e) {
			throw new AssertionError();
		}
	}

	public OkHttpClient getHttpClient() {
		return okHttpClient;
	}

	@Override
	public void log(@NonNull String message) {
		LogUtil.i("OkHttp: " + StringUtils.unescapeJava(message));
	}

	@Override
	public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
		Request request = chain.request();
		//缓存
		if (NetUtil.checkNetwork(AlarmApp.getContext()) == NetUtil.NO_NETWORK) {
			request = request.newBuilder()
					.cacheControl(CacheControl.FORCE_CACHE)
					.build();
			LogUtil.d("no network");
		}

		okhttp3.Response response = chain.proceed(request);
		//缓存响应
		if (NetUtil.checkNetwork(AlarmApp.getContext()) != NetUtil.NO_NETWORK) {
			//有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
			String cacheControl = request.cacheControl().toString();
			LogUtil.d("cacheControl=====" + cacheControl);
			return response.newBuilder()
					.header("Cache-Control", cacheControl)
//					.header("User-Agent", MyUtils.getUserAgent())
//					.header("imei", MyUtils.getIMEI())
					.removeHeader("Pragma")
					.build();
		} else {
			return response.newBuilder()
					.header("Cache-Control", "public, only-if-cached, max-stale=120")
//					.header("User-Agent", MyUtils.getUserAgent())
//					.header("imei", MyUtils.getIMEI())
					.removeHeader("Pragma")
					.build();
		}
	}

	private static class SingletonHolder {
		private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
	}

	public static RetrofitServiceManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public AlarmService getService() {
		return service;
	}
}
