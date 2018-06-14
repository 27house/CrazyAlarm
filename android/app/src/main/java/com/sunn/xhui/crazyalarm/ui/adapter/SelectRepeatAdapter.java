package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunn.xhui.crazyalarm.R;

/**
 * @author XHui.sun
 * created at 2018/5/16 0016  10:08
 */

public class SelectRepeatAdapter extends BaseRecycleAdapter {

	public SelectRepeatAdapter(Context context) {
		super(context);
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == BaseRecycleAdapter.V_TYPE_DATA) {
			View dataV = LayoutInflater.from(context).inflate(R.layout.item_create_repeat, parent, false);
			return new SelectRepeatViewHolder(dataV);
		}
		return super.onCreateViewHolder(parent, viewType);
	}


	public class SelectRepeatViewHolder extends BaseRecViewHolder {

		SelectRepeatViewHolder(View itemView) {
			super(itemView);
		}

		@Override
		public void setData(Object item) {
			super.setData(item);

		}
	}

}
