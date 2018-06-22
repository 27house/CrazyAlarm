package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Comment;
import com.sunn.xhui.crazyalarm.utils.FrescoBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author XHui.sun
 * created at 2018/6/21 0021  15:17
 */

public class CommentListAdapter extends BaseRecycleAdapter {
	private ClickCallback clickCallback;

	public CommentListAdapter(Context context, ClickCallback clickCallback) {
		super(context);
		this.clickCallback = clickCallback;
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == V_TYPE_DATA) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_list_comment, parent, false);
			return new ViewHolder(view);
		}
		return super.onCreateViewHolder(parent, viewType);
	}

	public void setCommentList(List<Comment> list) {
		setDataList(list);
	}

	public class ViewHolder extends BaseRecViewHolder {

		@BindView(R.id.iv_header)
		SimpleDraweeView ivHeader;
		@BindView(R.id.tv_nickname)
		TextView tvNickname;
		@BindView(R.id.tv_content)
		TextView tvContent;
		@BindView(R.id.tv_time)
		TextView tvTime;
		@BindView(R.id.rv_comment)
		RecyclerView rvComment;
		Comment comment;

		ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickCallback.clickComment(comment.getId(), comment.getUser().getId(), comment.getUser().getNickname());
				}
			});
		}

		@Override
		public void setData(Object item) {
			super.setData(item);
			comment = (Comment) item;
			FrescoBuilder.Start(context, ivHeader, comment.getUser().getAvatar())
					.setFailureImage(ContextCompat.getDrawable(context, R.mipmap.ic_default))
					.setIsCircle(true)
					.build();
			tvNickname.setText(comment.getUser().getNickname());
			tvTime.setText(comment.getCreate_time());
			tvContent.setText(comment.getContent());
			rvComment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
			rvComment.setAdapter(new SubCommentListAdapter(context, clickCallback, comment));
		}

	}

	public interface ClickCallback {
		void clickComment(int cId, long uId, String userNick);
	}
}
