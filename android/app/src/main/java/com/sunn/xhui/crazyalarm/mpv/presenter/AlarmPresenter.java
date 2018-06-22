package com.sunn.xhui.crazyalarm.mpv.presenter;

import com.sunn.xhui.crazyalarm.mpv.contract.AlarmContract;
import com.sunn.xhui.crazyalarm.mpv.model.AlarmModel;
import com.sunn.xhui.crazyalarm.net.resp.TaskListResp;
import com.sunn.xhui.crazyalarm.net.resp.VoiceListResp;

import rx.Subscriber;

public class AlarmPresenter extends BasePresenter implements AlarmContract.Presenter {

	private AlarmContract.View view;
	private AlarmContract.Model model;

	public AlarmPresenter(AlarmContract.View view) {
		this.view = view;
		model = new AlarmModel();
	}


	@Override
	public void getOnlineRing() {
		addSubscription(model.getRingtoneList().subscribe(new Subscriber<VoiceListResp>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				view.showTip("网络异常！");
			}

			@Override
			public void onNext(VoiceListResp voiceListResp) {
				if (view instanceof AlarmContract.RingtoneView){
					((AlarmContract.RingtoneView)view).returnRingtones(voiceListResp.getResult() == 0, voiceListResp.getList());
				}
				view.showTip(voiceListResp.getMessage());
			}
		}));
	}

	@Override
	public void getTaskList() {
		addSubscription(model.taskList().subscribe(new Subscriber<TaskListResp> () {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				view.showTip("网络异常！");
			}

			@Override
			public void  onNext(TaskListResp resp){
				if (view instanceof AlarmContract.TaskView){
					((AlarmContract.TaskView)view).returnTaskList(resp.getResult() == 0, resp.getList());
				}
				view.showTip(resp.getMessage());
			}
		}));
	}
}
