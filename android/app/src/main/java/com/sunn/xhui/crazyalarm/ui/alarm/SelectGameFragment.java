package com.sunn.xhui.crazyalarm.ui.alarm;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.AlarmGame;
import com.sunn.xhui.crazyalarm.mpv.contract.AlarmContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.AlarmPresenter;
import com.sunn.xhui.crazyalarm.ui.BaseFragment;
import com.sunn.xhui.crazyalarm.ui.adapter.AlarmGameListAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 选择结束闹铃的游戏
 *
 * @author XHui.sun
 * created at 2018/5/25 0025  10:53
 */
public class SelectGameFragment extends BaseFragment implements AlarmContract.TaskView{

	@BindView(R.id.rg_game)
	RadioGroup rgGame;
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	private SelectGameCallback callback;
	private AlarmGameListAdapter gameListAdapter;
	private int gameType;
	private AlarmPresenter presenter;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof SelectGameCallback) {
			callback = (SelectGameCallback) context;
		}
	}

	@Override
	protected int initLayoutId() {
		return R.layout.fragment_create_game;
	}

	@Override
	protected void activityCreated() {
		if (activity instanceof AddAlarmActivity) {
			AlarmGame currentGame = ((AddAlarmActivity) activity).currentGame;
			if (currentGame != null) {
				int id;
				if (currentGame.getType() == AlarmGame.TYPE_NORMAL) {
					id = R.id.rb_normal;
				} else if (currentGame.getType() == AlarmGame.TYPE_ONLY_VOICE) {
					id = R.id.rb_voice;
				} else {
					id = R.id.rb_vibrate;
				}
				RadioButton womanRb = rgGame.findViewById(id);
				womanRb.setChecked(true);
			}
		}

		gameListAdapter = new AlarmGameListAdapter(activity);
		recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(gameListAdapter);
		presenter = new AlarmPresenter(this);
		presenter.getTaskList();
	}

	public void commit() {
		switch (rgGame.getCheckedRadioButtonId()) {
			case R.id.rb_normal:
				gameType = AlarmGame.TYPE_NORMAL;
				break;
			case R.id.rb_voice:
				gameType = AlarmGame.TYPE_ONLY_VOICE;
				break;
			case R.id.rb_vibrate:
				gameType = AlarmGame.TYPE_ONLY_VIBRATE;
				break;
			default:
				gameType = AlarmGame.TYPE_NORMAL;
				break;
		}
		gameListAdapter.getSelectGame().setType(gameType);
		callback.selectedVoice(gameListAdapter.getSelectGame());
	}

	@Override
	public void showTip(String tip) {

	}

	@Override
	public void returnTaskList(boolean success, List<AlarmGame> list) {
		if (success) {
			gameListAdapter.setDGameList(list);
		}
	}

	public interface SelectGameCallback {
		/**
		 * 返回选择的类型
		 *
		 * @param game AlarmGame
		 */
		void selectedVoice(AlarmGame game);
	}
}
