package com.sunn.xhui.crazyalarm.ui.community;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunn.xhui.crazyalarm.R;
import com.sunn.xhui.crazyalarm.data.Comment;
import com.sunn.xhui.crazyalarm.data.Dynamic;
import com.sunn.xhui.crazyalarm.mpv.contract.DynamicContract;
import com.sunn.xhui.crazyalarm.mpv.presenter.DynamicPresenter;
import com.sunn.xhui.crazyalarm.net.req.SetDynamicReq;
import com.sunn.xhui.crazyalarm.ui.adapter.CommentListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XHui.sun
 * created at 2018/6/21 0021  14:12
 */
public class DynamicDetailsActivity extends AppCompatActivity implements DynamicContract.DetailsView, DynamicDetailsView.ClickCallback, CommentListAdapter.ClickCallback {

	public static final String EXTRA_DATA = "EXTRA_DATA";
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appBar)
	AppBarLayout appBar;
	@BindView(R.id.rv_comment)
	RecyclerView rvComment;
	@BindView(R.id.et_comment)
	EditText etComment;
	@BindView(R.id.iv_smile)
	ImageView ivSmile;
	@BindView(R.id.tv_send)
	TextView tvSend;
	private CommentListAdapter listAdapter;
	private DynamicPresenter presenter;
	private DynamicDetailsView detailsView;

	private Dynamic dynamic;

	private int followId;
	private int commentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_details);
		ButterKnife.bind(this);
		dynamic = (Dynamic) getIntent().getSerializableExtra(EXTRA_DATA);
		presenter = new DynamicPresenter(this);
		toolbar.setTitle(R.string.post);
		detailsView = new DynamicDetailsView(this, this);
		detailsView.fillView(dynamic);

		listAdapter = new CommentListAdapter(this, this);
		listAdapter.setHeadViews(detailsView);
		rvComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		rvComment.setAdapter(listAdapter);
		presenter.getCommentList(dynamic.getId(), 0, 30);
	}

	@OnClick(R.id.iv_smile)
	public void clickSmile(View view) {
		followId = (int) dynamic.getUser().getId();
		commentId = 0;
		etComment.setHint("");
	}

	@OnClick(R.id.tv_send)
	public void clickSend(View view) {
		if (etComment.getText().toString().length() > 0) {
			SetDynamicReq req = new SetDynamicReq(SetDynamicReq.TYPE_ADD_COMMENT);
			req.setdId(dynamic.getId());
			req.setcId(commentId);
			req.setFollowId(followId);
			req.setContent(etComment.getText().toString());
			presenter.setDynamic(req);
		}
	}

	@Override
	public void showTip(String tip) {

	}

	@Override
	public void dismissLoad() {

	}

	@Override
	public void returnSetResult(boolean success, int type, String tip, int did) {
		switch (type) {
			case SetDynamicReq.TYPE_ADD_LIKE:
				dynamic.setIsLike(1);
				dynamic.setLikeCount(dynamic.getLikeCount() + 1);
				detailsView.fillView(dynamic);
				break;
			case SetDynamicReq.TYPE_REMOVE_LIKE:
				dynamic.setIsLike(0);
				dynamic.setLikeCount(dynamic.getLikeCount() - 1);
				detailsView.fillView(dynamic);
				break;
			case SetDynamicReq.TYPE_DELETE:
				finish();
				break;
			case SetDynamicReq.TYPE_ADD_COMMENT:
				Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
				etComment.setText("");
				break;
			default:
				break;
		}
	}

	@Override
	public void clickHeart() {
		SetDynamicReq req = new SetDynamicReq(SetDynamicReq.TYPE_ADD_LIKE);
		if (dynamic.getIsLike() == 1) {
			req.setType(SetDynamicReq.TYPE_REMOVE_LIKE);
		}
		req.setcId(0);
		req.setdId(dynamic.getId());
		presenter.setDynamic(req);
	}

	@Override
	public void returnCommentList(List<Comment> list) {
		listAdapter.setCommentList(list);
	}

	@Override
	public void clickComment(int cId, long uId, String userNick) {
		followId = (int) uId;
		commentId = cId;
		etComment.setHint("对 " + userNick + " 说：");
	}
}
