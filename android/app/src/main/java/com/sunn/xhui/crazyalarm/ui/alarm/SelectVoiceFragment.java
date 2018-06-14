package com.sunn.xhui.crazyalarm.ui.alarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Voice;
import com.sunn.xhui.crazyalarm.ui.adapter.MainFragmentAdapter;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择音乐
 *
 * @author XHui.sun
 * created at 2018/5/16 0016  17:37
 */
public class SelectVoiceFragment extends BaseFragment {
	@BindView(R.id.tabLayout)
	TabLayout tabLayout;
	@BindView(R.id.view_pager)
	ViewPager viewPager;
	private List<Fragment> listFragment = new ArrayList<>();

	private SelectVoiceCallback callback;
	private MediaPlayer mediaPlayer;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof SelectVoiceCallback) {
			callback = (SelectVoiceCallback) context;
		}
	}

	@Override
	protected int initLayoutId() {
		return R.layout.fragment_create_voice;
	}

	@Override
	protected void activityCreated() {
		try {
			initAdapter();
			initTabItem();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	private void initAdapter() {
		listFragment.add(new VoiceLocalFragment(callback));
		listFragment.add(new VoiceOnlineFragment(callback));
		MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(activity.getSupportFragmentManager(), listFragment);
		viewPager.setAdapter(mainFragmentAdapter);
	}

	/**
	 * setText一定要放在setupWithViewPager之后，否则在tabLayout + viewPager + Fragment 的模式下不显示标题
	 */
	private void initTabItem() {
		// 第二个
		TabLayout.Tab tabOnline = tabLayout.newTab();
		tabLayout.addTab(tabOnline);
		// 第一个
		TabLayout.Tab tabLocal = tabLayout.newTab();
		tabLayout.addTab(tabLocal);
		// 使用 TabLayout 和 ViewPager 相关联
		tabLayout.setupWithViewPager(viewPager);
		tabLocal.setText(R.string.voice_local);
		tabOnline.setText(R.string.voice_online);
	}

	public void play(Voice voice){
		if (voice.getType() == Voice.TYPE_RING_TONE) {
			if (mediaPlayer != null && mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
			mediaPlayer = MediaPlayer.create(activity, Uri.parse(voice.getRingtone()));
			mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mediaPlayer.start();

				}
			});
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					LogUtil.e("--onCompletion--");
					mp.stop();
				}
			});
		} else {
			mediaPlayer = MediaPlayer.create(activity, Uri.fromFile(new File(voice.getPath())));
			mediaPlayer.start();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.e("--onDestroy--111");
		// 关闭播放器
		if (mediaPlayer != null) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public void updateDownloadData(Voice voice) {
		((VoiceLocalFragment)listFragment.get(0)).getDownloadList();
	}

	public interface SelectVoiceCallback {
		/**
		 * 返回选择的类型
		 *
		 * @param voice Voice
		 */
		void selectedVoice(Voice voice);
	}
}
