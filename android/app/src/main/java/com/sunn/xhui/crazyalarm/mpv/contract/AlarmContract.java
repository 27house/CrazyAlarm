package com.sunn.xhui.crazyalarm.mpv.contract;

import com.sunn.xhui.crazyalarm.data.AlarmGame;
import com.sunn.xhui.crazyalarm.data.Voice;
import com.sunn.xhui.crazyalarm.net.resp.TaskListResp;
import com.sunn.xhui.crazyalarm.net.resp.VoiceListResp;

import java.util.List;

import rx.Observable;


public interface AlarmContract {
	interface Model {
		Observable<VoiceListResp> getRingtoneList();

		Observable<TaskListResp> taskList();
	}

	interface View {
		void showTip(String tip);
	}

	interface RingtoneView extends View {
		void returnRingtones(boolean success, List<Voice> list);
	}

	interface TaskView extends View {
		void returnTaskList(boolean success, List<AlarmGame> list);
	}

	interface Presenter {
		void getOnlineRing();

		void getTaskList();
	}
}
