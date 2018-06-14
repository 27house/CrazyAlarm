package com.sunn.xhui.crazyalarm.ui.alarm;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunn.xhui.crazyalarm.Constant;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Voice;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.ui.adapter.VoiceListAdapter;
import com.sunn.xhui.crazyalarm.utils.FileUtils;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 本地音乐列表
 *
 * @author XHui.sun
 * created at 2018/5/17 0017  10:22
 */
@SuppressLint("ValidFragment")
public class VoiceLocalFragment extends BaseFragment implements VoiceListAdapter.SelectCallback {
	@BindView(R.id.rv_local)
	RecyclerView rvLocal;
	@BindView(R.id.rv_download)
	RecyclerView rvDownload;

	private List<Voice> localList = new ArrayList<>();
	private List<Voice> downloadList = new ArrayList<>();
	private VoiceListAdapter localAdapter;
	private VoiceListAdapter downloadAdapter;
	private SelectVoiceFragment.SelectVoiceCallback callback;

	public VoiceLocalFragment(SelectVoiceFragment.SelectVoiceCallback callback) {
		this.callback = callback;
	}

	@Override
	protected int initLayoutId() {
		return R.layout.fragment_create_voice_local;
	}

	@Override
	protected void activityCreated() {
		initView();
		getRingToneList();
		getDownloadList();
	}

	private void getRingToneList() {
		// 铃声管理器
		Observable.just(activity)
				.map(new Func1<AppCompatActivity, List<Voice>>() {
					@Override
					public List<Voice> call(AppCompatActivity appCompatActivity) {
						RingtoneManager ringtoneManager = new RingtoneManager(appCompatActivity);
						ringtoneManager.setType(RingtoneManager.TYPE_RINGTONE);
						Cursor cursor = ringtoneManager.getCursor();
						List<Voice> list = new ArrayList<>();
						while (cursor.moveToNext()) {
							// 获取铃声表,根据表名取值
							Voice voice = new Voice(Voice.TYPE_RING_TONE);
							voice.setId(cursor.getInt(cursor.getColumnIndex("_id")));
							voice.setTitle(cursor.getString(cursor.getColumnIndex("title")));
							voice.setCursorPos(cursor.getPosition());
							voice.setRingtone(ringtoneManager.getRingtoneUri(cursor.getPosition()).toString());
							list.add(voice);
						}

						return list;
					}
				})
				// 指定 subscribe() 发生在 IO 线程
				.subscribeOn(Schedulers.io())
				// 指定 Subscriber 的回调发生在主线程
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<List<Voice>>() {
					@Override
					public void onCompleted() {
						localAdapter.setVoiceList(localList);
					}

					@Override
					public void onError(Throwable e) {
						LogUtil.e(e.getMessage());
					}

					@Override
					public void onNext(List<Voice> voices) {
						localList = voices;
					}
				});
	}

	public void getDownloadList() {
		downloadList.clear();
		File folder = new File(FileUtils.getFilesPath(activity, Constant.FILE_NAME_RINGTONE));
		Observable.just(folder)
				.flatMap(new Func1<File, Observable<File>>() {
					@Override
					public Observable<File> call(File file) {
						return Observable.from(file.listFiles());
					}
				})
				.filter(new Func1<File, Boolean>() {
					@Override
					public Boolean call(File file) {
						return file.getName().endsWith(".mp3");
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<File>() {
					@Override
					public void call(File f) {
						if (f.getPath().endsWith(".mp3")) {
							Voice voice = new Voice(Voice.TYPE_DOWNLOAD);
							voice.setTitle(f.getName().substring(0, f.getName().lastIndexOf(".")));
							voice.setPath(f.getPath());
							downloadList.add(voice);
							downloadAdapter.setVoiceList(downloadList);
						}
					}
				});
	}

	private void initView() {
		rvLocal.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) {
			@Override
			public boolean canScrollVertically() {
				return false;
			}
		});
		rvDownload.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) {
			@Override
			public boolean canScrollVertically() {
				return false;
			}
		});

		localAdapter = new VoiceListAdapter(activity, this);
		downloadAdapter = new VoiceListAdapter(activity, this);

		rvLocal.setAdapter(localAdapter);
		rvDownload.setAdapter(downloadAdapter);
	}

	@Override
	public void play(Voice voice) {
		for (Voice v : localList) {
			if (v.getId() == voice.getId()) {
				v.setUsed(true);
			} else {
				v.setUsed(false);
			}
		}
		localAdapter.setVoiceList(localList);
		for (Voice v : downloadList) {
			if (v.getId() == voice.getId()) {
				v.setUsed(true);
			} else {
				v.setUsed(false);
			}
		}
		downloadAdapter.setVoiceList(downloadList);
		//获取到父fragment的管理器
		FragmentManager manager = getFragmentManager();
		if (manager != null) {
			//获取到父parentFragment
			SelectVoiceFragment home = (SelectVoiceFragment) manager.findFragmentByTag(SelectVoiceFragment.class.getSimpleName());
			if (home != null) {
				home.play(voice);
			}
		}
	}

	@Override
	public void used(Voice voice) {
		callback.selectedVoice(voice);
	}

	@Override
	public void download(Voice voice) {

	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
