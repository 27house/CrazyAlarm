package com.sunn.xhui.crazyalarm.ui.community;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import com.sunn.xhui.crazyalarm.ui.adapter.NightImageGridAdapter;
import com.sunn.xhui.crazyalarm.ui.widget.list.SpacesItemDecoration;
import com.sunn.xhui.crazyalarm.utils.DensityUtil;
import com.sunn.xhui.crazyalarm.utils.FrescoBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author XHui.sun
 * created at 2018/6/21 0021  15:06
 */

public class DynamicDetailsView extends RelativeLayout {
	@BindView(R.id.iv_header)
	SimpleDraweeView ivHeader;
	@BindView(R.id.tv_name)
	TextView tvName;
	@BindView(R.id.tv_time)
	TextView tvTime;
	@BindView(R.id.tv_content)
	TextView tvContent;
	@BindView(R.id.rv_pic)
	RecyclerView rvPic;
	@BindView(R.id.iv_single)
	SimpleDraweeView ivSingle;
	@BindView(R.id.rl_pic)
	RelativeLayout rlPic;
	@BindView(R.id.iv_like)
	ImageView ivLike;
	@BindView(R.id.tv_like_count)
	TextView tvLikeCount;
	@BindView(R.id.tv_comment)
	TextView tvComment;
	@BindView(R.id.tv_like)
	TextView tvLike;

	private int picMaxW;
	private ClickCallback clickCallback;

	public DynamicDetailsView(Context context, ClickCallback clickCallback) {
		super(context);
		this.clickCallback = clickCallback;
		initView();
	}

	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_dynamic_details, this);
		ButterKnife.bind(this);
		//添加ItemDecoration，item之间的间隔
		int leftRight = DensityUtil.dip2px(getContext(), 5);
		int topBottom = DensityUtil.dip2px(getContext(), 5);
		rvPic.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
		picMaxW = (AlarmApp.getScreenW(getContext()) - DensityUtil.dip2px(getContext(), 80));
	}


	public void fillView(Dynamic dynamic) {
		FrescoBuilder.Start(getContext(), ivHeader, dynamic.getUser().getAvatar())
				.setFailureImage(ContextCompat.getDrawable(getContext(), R.mipmap.ic_default))
				.setIsCircle(true)
				.build();
		tvName.setText(dynamic.getUser().getNickname());
		tvTime.setText(dynamic.getCreate_time());
		tvContent.setText(dynamic.getContent());
		// 图片
		if (dynamic.getPics().isEmpty()) {
			rvPic.setVisibility(View.GONE);
			ivSingle.setVisibility(View.GONE);
		} else {
			if (dynamic.getPics().size() == 1) {
				ivSingle.setVisibility(View.VISIBLE);
				rvPic.setVisibility(View.GONE);
				FrescoBuilder.Start(getContext(), ivSingle, dynamic.getPics().get(0))
						.setBackgroupImageColor(R.color.gray_f7)
						.setFailureImage(ContextCompat.getDrawable(getContext(), R.mipmap.ic_default))
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
				rvPic.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
				rvPic.setAdapter(new NightImageGridAdapter(getContext(), dynamic.getPics()));
			}
		}
		// 评论、点赞
		tvLikeCount.setText(String.valueOf(dynamic.getLikeCount()));
		tvLikeCount.setOnClickListener(clickLikeListener);
		ivLike.setOnClickListener(clickLikeListener);

		if (dynamic.getIsLike() == 1) {
			ivLike.setImageResource(R.mipmap.ic_like);
		} else {
			ivLike.setImageResource(R.mipmap.ic_like_normal);
		}
		tvComment.setText(String.format(getContext().getString(R.string.comment_count), dynamic.getCommentCount()));
		tvLike.setText(String.format(getContext().getString(R.string.like_count), dynamic.getLikeCount()));
	}

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

	private View.OnClickListener clickLikeListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			clickCallback.clickHeart();
		}
	};

	public interface ClickCallback {
		void clickHeart();
	}
}
