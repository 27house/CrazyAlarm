package com.sunn.xhui.crazyalarm.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
	private boolean hasMore;
	private int lastId;
	private Comment parent;

	public SubCommentListAdapter(Context context, CommentListAdapter.ClickCallback clickCallback, Comment parent) {
		super(context);
		this.clickCallback = clickCallback;
		this.parent = parent;
		List<Comment> list = parent.getSubList();
		this.hasMore = parent.getSubCount() > list.size();
		if (!list.isEmpty()) {
			lastId = list.get(list.size() - 1).getId();
		}
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
					// 所有子评论的cID都填父评论上
					clickCallback.clickComment(parent.getId(), comment.getUser().getId(), comment.getUser().getNickname());
				}
			});
		}

		@Override
		public void setData(Object item) {
			super.setData(item);
			comment = (Comment) item;
			tvComment.setText("");
			if (hasMore && lastId == comment.getId()) {
				// 最后一条 显示“查看更多”
				tvComment.append(getNickNameSpan("查看全部 " + parent.getSubCount() + " 条内容"));
			} else {
				tvComment.append(getNickNameSpan(comment.getUser().getNickname()));
				tvComment.append("对");
				tvComment.append(getNickNameSpan(comment.getFollowUser().getNickname()));
				tvComment.append("说：");
				tvComment.append(comment.getContent());
			}
		}

		private SpannableString getContentSpan(String content) {
			SpannableString spanString = new SpannableString(content);
			// 颜色
			ForegroundColorSpan span = new ForegroundColorSpan(Color.BLACK);
			spanString.setSpan(span, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			return spanString;
		}

		private SpannableString getNickNameSpan(String nickname) {

			SpannableString spanString = new SpannableString(nickname);
			// 颜色
			ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#4E9EE2"));
			spanString.setSpan(span, 0, nickname.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 加粗
//			StyleSpan span1 = new StyleSpan(Typeface.BOLD_ITALIC);
//			spanString.setSpan(span1, 0, nickname.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 点击
//			ClickableSpan span2 = new ClickableSpan() {
//				@Override
//				public void onClick(View widget) {
//
//				}
//			};
//			spanString.setSpan(span2, 0, nickname.length(), Spanned.SPAN_COMPOSING);
			return spanString;
		}

	}

}
