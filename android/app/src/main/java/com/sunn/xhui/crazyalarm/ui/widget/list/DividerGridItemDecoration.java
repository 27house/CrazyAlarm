package com.sunn.xhui.crazyalarm.ui.widget.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.sunn.xhui.crazyalarm.R;

/**
 * Grid-RecycleView 中间间距样式
 *
 * @author XHui.sun
 * created at 2016/11/2 0011  17:14
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

	private Drawable mDivider;
	private int startPos = 0;

	@SuppressWarnings("deprecation")
	public DividerGridItemDecoration(Context context) {
		mDivider = ContextCompat.getDrawable(context, R.drawable.bg_label);
	}

	private int horSpace = 1, verSpace = 1;

	public DividerGridItemDecoration(Context context, int divider, int horSpace, int verSpace) {
		this.horSpace = horSpace;
		this.verSpace = verSpace;
		this.mDivider = ContextCompat.getDrawable(context, divider);
	}

	public DividerGridItemDecoration(Context context, int divider, int horSpace, int verSpace, int startPos) {
		this.horSpace = horSpace;
		this.verSpace = verSpace;
		this.startPos = startPos;
		this.mDivider = ContextCompat.getDrawable(context, divider);
	}

	public DividerGridItemDecoration(Context context, int horSpace, int verSpace) {
		this.horSpace = horSpace;
		this.verSpace = verSpace;
		this.mDivider = ContextCompat.getDrawable(context, R.drawable.bg_label);
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDraw(c, parent, state);
		drawVertical(c, parent);
		drawHorizontal(c, parent);
	}

	private void drawVertical(Canvas c, RecyclerView parent) {
		// 画横线，判断左右间距

		for (int i = 0; i < parent.getChildCount(); i++) {
			View view = parent.getChildAt(i);
			int left = view.getLeft();
			int right = view.getRight();
			int startY = view.getBottom();
			int endY = view.getBottom() + verSpace;
			mDivider.setBounds(left, startY, right, endY);
			mDivider.draw(c);
		}

	}

	private void drawHorizontal(Canvas c, RecyclerView parent) {
		// 画竖线，判断上下间距
		for (int i = 0; i < parent.getChildCount(); i++) {
			View view = parent.getChildAt(i);
			int left = view.getLeft();
			int right = view.getLeft() + horSpace;

			int top = view.getTop();
			int bottom = view.getBottom();

			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
		int spanCount = getSpanCount(parent);
		int childCount = parent.getAdapter().getItemCount();
		if (isLastColum(parent, itemPosition + startPos, spanCount, childCount)) {
			// 如果是最后一列，则不需要绘制右边
			if (itemPosition >= startPos) {
				outRect.set(horSpace, 0, 0, verSpace);
			} else {
				outRect.set(0, 0, 0, 0);
			}
		} else {
			outRect.set(0, 0, horSpace, verSpace);
		}
	}

	private int getSpanCount(RecyclerView parent) {
		// 列数
		int spanCount = -1;
		RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager) {

			spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
		} else if (layoutManager instanceof StaggeredGridLayoutManager) {
			spanCount = ((StaggeredGridLayoutManager) layoutManager)
					.getSpanCount();
		}
		return spanCount;
	}

	private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
								int childCount) {
		RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager) {
			if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
			{
				return true;
			}
		} else if (layoutManager instanceof StaggeredGridLayoutManager) {
			int orientation = ((StaggeredGridLayoutManager) layoutManager)
					.getOrientation();
			if (orientation == StaggeredGridLayoutManager.VERTICAL) {
				if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
				{
					return true;
				}
			} else {
				childCount = childCount - childCount % spanCount;
				if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
					return true;
			}
		}
		return false;
	}

	private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
							  int childCount) {
		RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
		if (layoutManager instanceof GridLayoutManager) {
			childCount = childCount - childCount % spanCount;
			if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
				return true;
		} else if (layoutManager instanceof StaggeredGridLayoutManager) {
			int orientation = ((StaggeredGridLayoutManager) layoutManager)
					.getOrientation();
			// StaggeredGridLayoutManager 且纵向滚动
			if (orientation == StaggeredGridLayoutManager.VERTICAL) {
				childCount = childCount - childCount % spanCount;
				// 如果是最后一行，则不需要绘制底部
				if (pos >= childCount)
					return true;
			} else
			// StaggeredGridLayoutManager 且横向滚动
			{
				// 如果是最后一行，则不需要绘制底部
				if ((pos + 1) % spanCount == 0) {
					return true;
				}
			}
		}
		return false;
	}
}
