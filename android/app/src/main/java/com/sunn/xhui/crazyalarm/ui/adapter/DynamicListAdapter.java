package com.sunn.xhui.crazyalarm.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.sunn.xhui.crazyalarm.AlarmApp;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Dynamic;
import com.sunn.xhui.crazyalarm.ui.widget.list.SpacesItemDecoration;
import com.sunn.xhui.crazyalarm.utils.DensityUtil;
import com.sunn.xhui.crazyalarm.utils.FileUtils;
import com.sunn.xhui.crazyalarm.utils.FrescoBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XHui.sun
 * created at 2018/6/15 0015  15:36
 */

public class DynamicListAdapter extends BaseRecycleAdapter {

	private List<Dynamic> list;
	private ItemClick itemClick;

	public DynamicListAdapter(Context context, ItemClick itemClick) {
		super(context);
		this.itemClick = itemClick;
	}

	@Override
	public List<Dynamic> getDataList() {
		return list;
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == BaseRecycleAdapter.V_TYPE_DATA) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_list_dynamic, parent, false);
			return new ViewHolder(view);
		}
		return super.onCreateViewHolder(parent, viewType);
	}

	public void setDynamicList(List<Dynamic> list) {
		this.list = list;
		setDataList(list);
	}

	public class ViewHolder extends BaseRecViewHolder {
		@BindView(R.id.iv_header)
		SimpleDraweeView ivHeader;
		@BindView(R.id.tv_name)
		TextView tvName;
		@BindView(R.id.tv_time)
		TextView tvTime;
		@BindView(R.id.tv_content)
		TextView tvContent;
		@BindView(R.id.rl_pic)
		RelativeLayout rlPic;
		@BindView(R.id.rv_pic)
		RecyclerView rvPic;
		@BindView(R.id.iv_single)
		SimpleDraweeView ivSingle;
		@BindView(R.id.tv_like_count)
		TextView tvLike;
		@BindView(R.id.iv_like)
		ImageView ivLike;
		@BindView(R.id.tv_comment_count)
		TextView tvComment;
		@BindView(R.id.iv_comment)
		ImageView ivComment;
		@BindView(R.id.iv_delete)
		ImageView ivDelete;
		private Dynamic dynamic;
		private int picMaxW;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);

			//添加ItemDecoration，item之间的间隔
			int leftRight = DensityUtil.dip2px(context, 5);
			int topBottom = DensityUtil.dip2px(context, 5);
			rvPic.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
			picMaxW = (AlarmApp.getScreenW(context) - DensityUtil.dip2px(context, 80));
		}

		@OnClick(R.id.iv_delete)
		public void clickDelete(View view) {
			// 删除
			itemClick.clickDelete(dynamic.getId());
		}

		@SuppressLint("SetTextI18n")
		@Override
		public void setData(Object item) {
			super.setData(item);
			dynamic = (Dynamic) item;
			FrescoBuilder.Start(context, ivHeader, dynamic.getUser().getAvatar())
					.setFailureImage(ContextCompat.getDrawable(context, R.mipmap.ic_default))
					.setIsCircle(true)
					.build();
			tvName.setText(dynamic.getUser().getNickname());
			tvTime.setText(dynamic.getCreate_time());
			tvContent.setText(dynamic.getContent());
			// 图片
			if (dynamic.getUser().getAccount().equals(AlarmApp.getAccount())) {
				ivDelete.setVisibility(View.VISIBLE);
			} else {
				ivDelete.setVisibility(View.GONE);
			}
			if (dynamic.getPics().isEmpty()) {
				rvPic.setVisibility(View.GONE);
				ivSingle.setVisibility(View.GONE);
			} else {
				if (dynamic.getPics().size() == 1) {
					ivSingle.setVisibility(View.VISIBLE);
					rvPic.setVisibility(View.GONE);
					FrescoBuilder.Start(context, ivSingle, dynamic.getPics().get(0))
							.setBackgroupImageColor(R.color.gray_f7)
							.setFailureImage(ContextCompat.getDrawable(context, R.mipmap.ic_default))
							.setBitmapDataSubscriber(new BaseBitmapDataSubscriber() {
								@Override
								protected void onNewResultImpl(final Bitmap bitmap) {
									if (bitmap == null) {
										return;
									}
									ivSingle.post(new Runnable() {
										@Override
										public void run() {
											int height = bitmap.getHeight();
											int width = bitmap.getWidth();
											int imageWidth = width > height ? picMaxW * 2 / 3 : picMaxW / 2;
											final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivSingle.getLayoutParams();
											layoutParams.width = imageWidth;
											layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
											ivSingle.setLayoutParams(layoutParams);
										}
									});
								}

								@Override
								protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
								}
							})
							.build();
				} else {
					rvPic.setVisibility(View.VISIBLE);
					ivSingle.setVisibility(View.GONE);
					int spanCount = getCul(dynamic.getPics().size());
					rvPic.setLayoutManager(new GridLayoutManager(context, spanCount));
					rvPic.setAdapter(new NightImageGridAdapter(context, dynamic.getPics()));
				}
			}
			// 评论、点赞
			tvLike.setText(dynamic.getLikeCount() + "");
			tvComment.setText(dynamic.getCommentCount() + "");
			tvLike.setOnClickListener(clickLikeListener);
			ivLike.setOnClickListener(clickLikeListener);
			tvComment.setOnClickListener(clickCommentListener);
			ivComment.setOnClickListener(clickCommentListener);

			if (dynamic.getIsLike() == 1) {
				ivLike.setImageResource(R.mipmap.ic_like);
			} else {
				ivLike.setImageResource(R.mipmap.ic_like_normal);
			}

		}

		private View.OnClickListener clickLikeListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemClick.clickLike(dynamic.getIsLike() == 1, dynamic.getId());
			}
		};
		private View.OnClickListener clickCommentListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemClick.clickComment(dynamic.getId());
			}
		};

		private int getCul(int total) {
			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rlPic.getLayoutParams();
			switch (total) {
				case 2:
				case 4:
					lp.width = picMaxW - 40;
					rlPic.setLayoutParams(lp);
					return 2;
				case 3:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				default:
					lp.width = picMaxW;
					rlPic.setLayoutParams(lp);
					return 3;

			}
		}
	}

	public interface ItemClick {
		void clickLike(boolean isLike, int did);

		void clickComment(int did);

		void clickDelete(int did);
	}
}
