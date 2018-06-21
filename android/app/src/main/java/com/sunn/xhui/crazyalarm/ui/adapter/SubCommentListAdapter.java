package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author XHui.sun
 * created at 2018/6/21 0021  15:17
 */

public class SubCommentListAdapter extends BaseRecycleAdapter {
	private CommentListAdapter.ClickCallback clickCallback;

	public SubCommentListAdapter(Context context, CommentListAdapter.ClickCallback clickCallback, List<Comment> list) {
		super(context);
		this.clickCallback = clickCallback;
		setDataList(list);
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == V_TYPE_DATA) {
			View view = LayoutInflater.from(context).inflate(R.layout.item_list_comment_sub, parent, false);
			return new ViewHolder(view);
		}
		return super.onCreateViewHolder(parent, viewType);
	}

	public class ViewHolder extends BaseRecViewHolder {

		@BindView(R.id.tv_comment)
		TextView tvComment;
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
			String text = comment.getUser().getNickname() + "对" + comment.getFollowId() + "说：" + comment.getContent();
			tvComment.setText(text);
		}

	}

}
