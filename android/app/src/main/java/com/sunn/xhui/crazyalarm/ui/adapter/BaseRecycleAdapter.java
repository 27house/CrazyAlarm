package com.sunn.xhui.crazyalarm.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunn.xhui.crazyalarm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * RecycleView的统一定制Adapter（方便统一添加header View和foot View）
 *
 * @author XHui.sun
 *         created at 2017/10/26 0026  18:05
 */
public class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecViewHolder> {
	@SuppressLint("UseSparseArrays")
	private Map<Integer, BaseRecViewHolder> viewHolderMap = new HashMap<>();

	protected Context context;

	private ClickLoadMore clickLoadMore;
	private String endMsg;

	BaseRecycleAdapter(Context context) {
		this.context = context;
		endMsg = context.getString(R.string.is_bottom);
	}

	static final int V_TYPE_DATA = 0;
	private static final int V_TYPE_FOOTER = 1;
	private List<View> headerVs = new ArrayList<>();

	public void setClickLoadMore(ClickLoadMore clickLoadMore) {
		this.clickLoadMore = clickLoadMore;
	}

	public void setHeadViews(View... views) {
		headerVs = Arrays.asList(views);
		notifyDataSetChanged();
	}

	private List<Object> dataList = new ArrayList<>();

	void setDataList(List<Object> dataList) {
		this.dataList = dataList;
		notifyDataSetChanged();
	}

	public List<Object> getDataList() {
		return dataList;
	}

	private boolean isShowFooter;

	public void showFooterV(boolean show) {
		isShowFooter = show;
	}

	public BaseRecViewHolder getVHolderMapFromPos(int pos) {
		return viewHolderMap.get(pos);
	}

	@Override
	public int getItemViewType(int position) {
		if (position < headerVs.size()) {
			// 头部 // 返回负数，方便获取View的下标
			return position - headerVs.size();
		} else if (position == headerVs.size() + dataList.size() && isShowFooter) {
			// 加载更多
			return V_TYPE_FOOTER;
		} else {
			// 数据
			return V_TYPE_DATA;
		}
	}

	@NonNull
	@Override
	public BaseRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == V_TYPE_DATA) {
			return new BaseRecViewHolder(null);
		} else if (viewType == V_TYPE_FOOTER) {
			View view = LayoutInflater.from(context).inflate(R.layout.view_recy_footer, parent, false);
			return new FooterVH(view);
		} else {
			return new BaseRecViewHolder(headerVs.get(viewType + headerVs.size()));
		}
	}

	@Override
	public void onBindViewHolder(@NonNull BaseRecViewHolder holder, int position) {
		if (getItemViewType(position) == V_TYPE_DATA && position - headerVs.size() < dataList.size()) {
			holder.setData(dataList.get(position - headerVs.size()));
		} else if (getItemViewType(position) == V_TYPE_FOOTER && isShowFooter) {
			//noinspection unchecked
			((FooterVH) holder).tvFooter.setText(loadMore ? context.getString(R.string.load_more) : endMsg);
			//noinspection unchecked
			((FooterVH) holder).progressBar.setVisibility(loadMore ? View.VISIBLE : View.GONE);
		}
		//noinspection unchecked
		viewHolderMap.put(position, holder);
	}

	private boolean loadMore = true;

	public void setLoadMore(boolean loadMore) {
		this.loadMore = loadMore;
		notifyDataSetChanged();
	}
	public boolean isLoadMore(){
		return this.loadMore;
	}

	public void setEndMsg(String endMsg) {
		if (TextUtils.isEmpty(endMsg)){
			endMsg = context.getString(R.string.is_bottom);
		}
		this.endMsg = endMsg;
	}

	public String getEndMsg() {
		return endMsg;
	}

	@Override
	public int getItemCount() {
		return headerVs.size() + dataList.size() + (isShowFooter ? 1 : 0);
	}

	class FooterVH extends BaseRecViewHolder {

		@BindView(R.id.tv_footer)
		TextView tvFooter;
		@BindView(R.id.progressBar)
		ProgressBar progressBar;

		FooterVH(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@OnClick(R.id.tv_footer)
		public void clickLoadMore(View view) {
			if (clickLoadMore != null) {
				loadMore = true;
				notifyDataSetChanged();
				clickLoadMore.clickLoadMore();
			}
		}

	}

	public interface ClickLoadMore {
		void clickLoadMore();
	}
}
