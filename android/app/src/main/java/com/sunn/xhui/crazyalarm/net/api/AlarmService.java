package com.sunn.xhui.crazyalarm.net.api;

import com.sunn.xhui.crazyalarm.net.resp.BaseResp;
import com.sunn.xhui.crazyalarm.net.resp.CommentListResp;
import com.sunn.xhui.crazyalarm.net.resp.DynamicListResp;
import com.sunn.xhui.crazyalarm.net.resp.LoginResp;
import com.sunn.xhui.crazyalarm.net.resp.TaskListResp;
import com.sunn.xhui.crazyalarm.net.resp.VoiceListResp;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public interface AlarmService {
	@FormUrlEncoded
	@POST("login")
	Observable<LoginResp> login(@Field("account") String account, @Field("password") String password);

	@FormUrlEncoded
	@POST("register")
	Observable<BaseResp> register(@Field("account") String account, @Field("password") String password);

	@Multipart
	@POST("uploadAvatar")
	Observable<BaseResp> uploadAvatar(@Part MultipartBody.Part userLogo, @Part("account") RequestBody account);

	@FormUrlEncoded
	@POST("updateInfo")
	Observable<BaseResp> updateInfo(@Field("type") int type, @Field("value") String value, @Field("account") String account);

	@GET("getUserInfo")
	Observable<LoginResp> getUserInfo(@Query("account") String account);

	@GET("query_alarm?type=ringtone")
	Observable<VoiceListResp> ringtoneList();

	@GET("query_alarm?type=task")
	Observable<TaskListResp> taskList();

	@GET
	Observable<ResponseBody> downloadFile(@Url String fileUrl);

	@GET("dynamic?type=getList")
	Observable<DynamicListResp> getDynamicList(@Query("page") int page, @Query("page_count") int page_count, @Query("account") String account);

	@Multipart
	@POST("dynamic")
	Observable<BaseResp> addDynamic(@Part() List<MultipartBody.Part> parts, @Part("type") RequestBody type, @Part("account") RequestBody account, @Part("content") RequestBody content);

	@FormUrlEncoded
	@POST("dynamic?type=add_like")
	Observable<BaseResp> addDynamicLike(@Field("account") String account, @Field("d_id") int dId, @Field("c_id") int cId);

	@FormUrlEncoded
	@POST("dynamic?type=delete_like")
	Observable<BaseResp> removeDynamicLike(@Field("account") String account, @Field("d_id") int dId, @Field("c_id") int cId);

	@FormUrlEncoded
	@POST("dynamic?type=delete_dynamic")
	Observable<BaseResp> deleteDynamic(@Field("account") String account, @Field("d_id") int dId, @Field("c_id") int cId);

	@FormUrlEncoded
	@POST("dynamic?type=add_comment")
	Observable<BaseResp> sendComment(@Field("account") String account, @Field("d_id") int dId,
									 @Field("c_id") int cId, @Field("f_id") int fId, @Field("content") String content);

	@GET("dynamic?type=getCommentList")
	Observable<CommentListResp> getCommentList(@Query("d_id") int dId, @Query("page") int page, @Query("page_count") int page_count, @Query("account") String account);
}
