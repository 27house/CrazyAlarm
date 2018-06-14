package com.sunn.xhui.crazyalarm.ui.alarm;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

import com.sunn.xhui.crazyalarm.Constant;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Voice;
import com.sunn.xhui.crazyalarm.mpv.contract.AlarmContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.AlarmPresenter;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.ui.adapter.VoiceListAdapter;
import com.sunn.xhui.crazyalarm.utils.FileUtils;
//import com.sunn.xhui.crazyalarm.utils.FileUtils
import java.io.File;
import java.util.*;

/**
 * 在线音乐列表
 *
 * @author XHui.sun
 * created at 2018/5/17 0017  10:22
 */
@SuppressLint("ValidFragment")
public class VoiceOnlineFragment extends BaseFragment
		implements VoiceListAdapter.SelectCallback, SwipeRefreshLayout.OnRefreshListener, AlarmContract.RingtoneView {


	private SelectVoiceFragment.SelectVoiceCallback callback;
	@BindView(R.id.rv_online)
	RecyclerView rvOnline;
	@BindView(R.id.swipeRefreshLayout)
	SwipeRefreshLayout swipeRefreshLayout;
	private VoiceListAdapter onlineAdapter;
	private AlarmPresenter presenter;
	private List<Voice> onlineList;

	public VoiceOnlineFragment(SelectVoiceFragment.SelectVoiceCallback callback) {
		this.callback = callback;
	}

	@Override
	protected int initLayoutId() {
		return R.layout.fragment_create_voice_online;
	}

	@Override
	protected void activityCreated() {
		try {
			presenter = new AlarmPresenter(this);
			onlineAdapter = new VoiceListAdapter(activity, this);
			rvOnline.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
			rvOnline.setAdapter(onlineAdapter);
			swipeRefreshLayout.setOnRefreshListener(this);
			presenter.getOnlineRing();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void play(Voice voice) {
		for (Voice v : onlineList) {
			if (v.getId() == voice.getId()) {
				v.setUsed(true);
			} else {
				v.setUsed(false);
			}
		}
		onlineAdapter.setVoiceList(onlineList);
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
		// 下载成功 -- 通知localFragment刷新数据
		//获取到父fragment的管理器
		FragmentManager manager = getFragmentManager();
		if (manager != null) {
			//获取到父parentFragment
			SelectVoiceFragment home = (SelectVoiceFragment) manager.findFragmentByTag(SelectVoiceFragment.class.getSimpleName());
			if (home != null) {
				home.updateDownloadData(voice);
			}
		}
	}

	@Override
	public void onRefresh() {
		presenter.getOnlineRing();
	}


	@Override
	public void showTip(String tip) {
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void returnRingtones(boolean success, List<Voice> list) {
		if (success) {
			File folder = new File(FileUtils.getFilesPath(activity, Constant.FILE_NAME_RINGTONE));
			File[] files = folder.listFiles();
			for (Voice voice : list) {
				voice.setType(Voice.TYPE_ONLINE);
				for (File file : files) {
					String fn = file.getName().substring(0, file.getName().lastIndexOf("."));
					if (fn.equals(voice.getTitle())) {
						voice.setType(Voice.TYPE_DOWNLOAD);
					}
				}
			}
			onlineList = list;
			onlineAdapter.setVoiceList(list);
		}
	}

}
