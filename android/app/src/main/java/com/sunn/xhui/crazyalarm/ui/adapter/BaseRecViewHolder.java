package com.sunn.xhui.crazyalarm.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 基础类
 *
 * @author XHui.sun
 * created at 2018/5/16 0016  10:11
 */
public class BaseRecViewHolder extends RecyclerView.ViewHolder {
	private View itemView;

	BaseRecViewHolder(View itemView) {
		super(itemView);
		this.itemView = itemView;
	}

	public View getItemView() {
		return itemView;
	}

	public  void setData(Object item) {
	}
}
