package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.utils.FrescoBuilder;
import com.sunn.xhui.crazyalarm.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NightImageGridAdapter extends BaseRecycleAdapter {
	private ArrayList<String> imgList = new ArrayList<>();

	public NightImageGridAdapter(Context context, List<String> imgPaths) {
		super(context);
		setImgList(imgPaths);
	}

	private void setImgList(List<String> imgPaths) {
		imgList.clear();
		imgList.addAll(imgPaths);
		setDataList(imgList);
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LogUtil.e("viewType--" + viewType);
		if (viewType == V_TYPE_DATA) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_img, parent, false);
			return new ViewHolder(view);
		}
		return super.onCreateViewHolder(parent, viewType);

	}

	public class ViewHolder extends BaseRecViewHolder {
		@BindView(R.id.image)
		SimpleDraweeView imageView;
		private String path;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public  void setData(Object item) {
			super.setData(item);
			path = (String) item;
			FrescoBuilder.Start(context, imageView, path)
					.setFailureImage(ContextCompat.getDrawable(context, R.mipmap.ic_default))
					.build();
		}

		@OnClick(R.id.image)
		public void clickImg(View view) {
//			OpenActHelper.getInstance(context).openImageAct(imgList, path, false);
		}
	}
}
