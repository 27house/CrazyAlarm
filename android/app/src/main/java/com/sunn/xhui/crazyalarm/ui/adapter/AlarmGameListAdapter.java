package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.AlarmGame;
import com.sunn.xhui.crazyalarm.ui.alarm.AddAlarmActivity;
import com.sunn.xhui.crazyalarm.ui.widget.ProgressButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XHui.sun
 * created at 2018/5/25 0025  11:29
 */

public class AlarmGameListAdapter extends BaseRecycleAdapter {

	private int selectId = -1;
	private List<AlarmGame> gameList;

	public AlarmGameListAdapter(Context context) {
		super(context);
	}

	public void setDGameList(List<AlarmGame> dataList) {
		gameList = dataList;
		super.setDataList(gameList);
		if (context instanceof AddAlarmActivity) {
			AddAlarmActivity activity = (AddAlarmActivity) context;
			if (activity.currentGame != null) {
				selectId = activity.currentGame.getId();
			}
		}
	}

	private void setSelectId(int id) {
		this.selectId = id;
		notifyDataSetChanged();
	}

	public AlarmGame getSelectGame() {
		for (AlarmGame game : gameList) {
			if (selectId == game.getId()) {
				return game;
			}
		}
		return null;
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == V_TYPE_DATA) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_create_voice, parent, false);
			return new ViewHolder(view);
		}
		return super.onCreateViewHolder(parent, viewType);
	}

	public class ViewHolder extends BaseRecViewHolder {

		@BindView(R.id.iv_icon)
		ImageView ivIcon;
		@BindView(R.id.tv_title)
		TextView tvTitle;
		@BindView(R.id.btn_voice)
		ProgressButton btnVoice;
		@BindView(R.id.ll_content)
		LinearLayout llContent;
		private AlarmGame game;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void setData(Object item) {
			super.setData(item);
			game = (AlarmGame) item;
			tvTitle.setText(game.getName());
			btnVoice.setText(R.string.try_play);
			ivIcon.setImageResource(R.drawable.ic_game);
			if (selectId == game.getId()) {
				tvTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
				ivIcon.setVisibility(View.VISIBLE);
			} else {
				tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray_33));
				ivIcon.setVisibility(View.GONE);
			}

		}

		@OnClick(R.id.ll_content)
		public void clickItem(View view) {
			setSelectId(game.getId());
		}
	}
}
