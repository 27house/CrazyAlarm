package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.Constant;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Voice;
import com.sunn.xhui.crazyalarm.mpv.contract.DownloadContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.DownloadPresenter;
import com.sunn.xhui.crazyalarm.ui.alarm.AddAlarmActivity;
import com.sunn.xhui.crazyalarm.ui.widget.ProgressButton;
import com.sunn.xhui.crazyalarm.utils.FileUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 音频列表
 *
 * @author XHui.sun
 * created at 2018/5/17 0017  10:44
 */
public class VoiceListAdapter extends BaseRecycleAdapter {

	private SelectCallback callback;

	public VoiceListAdapter(Context context, SelectCallback callback) {
		super(context);
		this.callback = callback;
	}

	public void setVoiceList(List<Voice> list) {
		setDataList(list);
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == BaseRecycleAdapter.V_TYPE_DATA) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_create_voice, parent, false);
			return new VoiceViewHolder(view);
		}
		return super.onCreateViewHolder(parent, viewType);
	}

	public class VoiceViewHolder extends BaseRecViewHolder implements ProgressButton.OnProgressButtonClickListener {

		@BindView(R.id.iv_icon)
		ImageView ivIcon;
		@BindView(R.id.tv_title)
		TextView tvTitle;
		@BindView(R.id.btn_voice)
		ProgressButton btnVoice;
		private Voice voice;

		VoiceViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void setData(Object item) {
			super.setData(item);
			voice = (Voice) item;
			switch (voice.getType()) {
				case Voice.TYPE_RING_TONE:
					// 铃声
				case Voice.TYPE_DOWNLOAD:
					// 已下载
					btnVoice.setText(R.string.used);
					break;
				case Voice.TYPE_ONLINE:
					// 在线
					btnVoice.setText(R.string.download);
					break;
				default:
					break;
			}
			if (context instanceof AddAlarmActivity) {
				AddAlarmActivity activity = (AddAlarmActivity) context;
				if (activity.currentVoice != null && activity.currentVoice.getTitle().equals(voice.getTitle())) {
					btnVoice.setText(R.string.already_used);
					btnVoice.setForeground(ContextCompat.getColor(context, R.color.gray_cd));
				}
			} else {
				btnVoice.setForeground(ContextCompat.getColor(context, R.color.colorPrimary));
			}
			if (voice.isUsed()) {
				// 正在拨放中
				tvTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
				ivIcon.setVisibility(View.VISIBLE);
			} else {
				// 没有被选中播放
				tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray_33));
				ivIcon.setVisibility(View.GONE);
			}
			tvTitle.setText(voice.getTitle());
			btnVoice.setOnProgressButtonClickListener(this);
		}

		@OnClick(R.id.ll_content)
		public void clickItem(View view) {
			// 播放
			callback.play(voice);
		}

		@Override
		public void onClickListener() {
			switch (voice.getType()) {
				case Voice.TYPE_RING_TONE:
				case Voice.TYPE_DOWNLOAD:
					//  铃声 、已下载 -- 确定选择
					callback.used(voice);
					break;
				case Voice.TYPE_ONLINE:
					// 在线 -- 下载（下载成功后改为使用）
					final String fpn = FileUtils.getFilesPath(context, Constant.FILE_NAME_RINGTONE) + "/" + voice.getTitle() + ".mp3";
					DownloadPresenter presenter = new DownloadPresenter(new DownloadContract.View() {

						@Override
						public void downloadSuccess() {
							btnVoice.setText("使用");
							btnVoice.setProgress(100);
							voice.setPath(fpn);
							voice.setType(Voice.TYPE_DOWNLOAD);
							callback.download(voice);
						}

						@Override
						public void downloadProcess(int progress) {
							btnVoice.setText("正在下载...");
							btnVoice.setProgress(progress);
						}

						@Override
						public void downloadFailed(String error) {
							btnVoice.setText("下载");
						}

						@Override
						public String filePathName() {
							// 下载保存地址
							return fpn;
						}
					});
					presenter.downloadFile(voice.getUrl());
					break;
				default:
					break;
			}
		}
	}

	public interface SelectCallback {
		/**
		 * 选中-去播放
		 *
		 * @param voice Voice
		 */
		void play(Voice voice);

		/**
		 * 选中-确定使用，关闭页面并返回
		 *
		 * @param voice Voice
		 */
		void used(Voice voice);

		/**
		 * 选中-去下载，并在本地中显示
		 *
		 * @param voice Voice
		 */
		void download(Voice voice);
	}
}



