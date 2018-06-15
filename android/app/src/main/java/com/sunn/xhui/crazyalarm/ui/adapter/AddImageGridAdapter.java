package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.SelectImg;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddImageGridAdapter extends BaseRecycleAdapter {
	private ClickCallback clickCallback;
	private List<SelectImg> imgList = new ArrayList<>();

	public AddImageGridAdapter(Context context, ClickCallback clickCallback) {
		super(context);
		this.clickCallback = clickCallback;
		setImgList(new ArrayList<SelectImg>());
	}

	public void setImgList(List<SelectImg> imgPaths) {
		imgList.clear();
		imgList.addAll(imgPaths);
		if (imgList.size() < 9) {
			SelectImg addIcon = new SelectImg();
			addIcon.setType(SelectImg.TYPE_ADD);
			imgList.add(addIcon);
		}
		setDataList(imgList);
	}

	@Override
	public BaseRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == V_TYPE_DATA) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_img_grid, parent, false);
			return new ViewHolder(view);
		}
		return super.onCreateViewHolder(parent, viewType);

	}

	public class ViewHolder extends BaseRecViewHolder {
		@BindView(R.id.image)
		ImageView ivFeedback;
		@BindView(R.id.view_bg)
		View viewBg;
		@BindView(R.id.tv_upload)
		TextView tvUpload;
		@BindView(R.id.progressBar)
		ProgressBar progressBar;
		@BindView(R.id.iv_clear)
		ImageView ivClear;
		private SelectImg selectImg;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void setData(Object item) {
			super.setData(item);
			selectImg = (SelectImg) item;
			if (selectImg.getType() == SelectImg.TYPE_ADD) {
				ivFeedback.setImageResource(R.mipmap.ic_add);
				ivClear.setVisibility(View.GONE);
				viewBg.setVisibility(View.GONE);
				tvUpload.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);
			} else {
				switch (selectImg.getStatus()) {
					case SelectImg.STATUS_DEFAULT:
						ivClear.setVisibility(View.VISIBLE);
						viewBg.setVisibility(View.GONE);
						tvUpload.setVisibility(View.GONE);
						progressBar.setVisibility(View.GONE);
						break;
					case SelectImg.STATUS_UPLOAD:
						ivClear.setVisibility(View.GONE);
						viewBg.setVisibility(View.VISIBLE);
						tvUpload.setVisibility(View.GONE);
						progressBar.setVisibility(View.VISIBLE);
						break;
					case SelectImg.STATUS_SUCCESS:
						ivClear.setVisibility(View.GONE);
						viewBg.setVisibility(View.GONE);
						tvUpload.setVisibility(View.GONE);
						progressBar.setVisibility(View.GONE);
						break;
					case SelectImg.STATUS_ERROR:
						ivClear.setVisibility(View.VISIBLE);
						viewBg.setVisibility(View.VISIBLE);
						tvUpload.setVisibility(View.VISIBLE);
						progressBar.setVisibility(View.GONE);
						break;
					default:
						break;
				}
				ivFeedback.setImageBitmap(selectImg.getThumb());
			}
		}

		@OnClick(R.id.image)
		public void clickImg(View view) {
			if (selectImg.getType() == SelectImg.TYPE_ADD) {
				clickCallback.addPic();
				// ADD
			} else {
				// Open Big
			}
		}

		@OnClick(R.id.iv_clear)
		public void clickClear(View v) {
			clickCallback.removeItem(selectImg);
		}

		@OnClick(R.id.tv_upload)
		public void clickUpload(View v) {
			clickCallback.uploadItem(selectImg);
		}
	}

	public interface ClickCallback {
		void addPic();

		void removeItem(SelectImg selectImg);

		void uploadItem(SelectImg selectImg);
	}
}
