package com.sunn.crazy.servlet;

import com.sunn.crazy.Constant;
import com.sunn.crazy.bean.Dynamic;
import com.sunn.crazy.bean.LikeBean;
import com.sunn.crazy.bean.UserBean;
import com.sunn.crazy.service.DBService;
import com.sunn.crazy.utils.TextUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dynamic")
@MultipartConfig
public class DynamicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");
        String account = req.getParameter("account");
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(account)) {
            UserBean user = DBService.getService().getUserBean(account);
            if (user != null) {
                switch (type) {
                    case "add_dynamic":
                        boolean success = DBService.getService().putDynamic(user.getId(), req, this);
                        if (success) {
                            jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                            jsonObject.put(Constant.KEY_MESSAGE, "发布成功！");
                        } else {
                            jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_DB);
                            jsonObject.put(Constant.KEY_MESSAGE, "发布失败！");
                        }
                        break;
                    case "delete_dynamic":
                        DBService.getService().removeDynamic(user.getId(), req.getParameter("d_id"));
                        jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                        jsonObject.put(Constant.KEY_MESSAGE, "删除成功！");
                        break;
                    case "add_like":
                    case "delete_like":
                        String dId = req.getParameter("d_id");
                        String cId = req.getParameter("c_id");
                        if (type.equals("add_like")) {
                            boolean put = DBService.getService().putLikeDy(user.getId(), dId, cId);
                            if (put) {
                                jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                                jsonObject.put(Constant.KEY_MESSAGE, "点赞成功！");
                                break;
                            }
                            jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_DB);
                            jsonObject.put(Constant.KEY_MESSAGE, "点赞失败！");
                        } else {
                            DBService.getService().removeLikeDy(user.getId(), dId, cId);
                            jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                            jsonObject.put(Constant.KEY_MESSAGE, "已取消赞！");
                            break;
                        }
                        break;
                    case "add_comment":
                        break;
                    case "delete_comment":
                        break;
                    default:
                        break;
                }
            } else {
                jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_EMPTY_USER);
                jsonObject.put(Constant.KEY_MESSAGE, Constant.Msg.ERROR_EMPTY_USER);
            }
        } else {
            jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_NULL_PARAM);
            jsonObject.put(Constant.KEY_MESSAGE, Constant.Msg.ERROR_NULL_PARAM);
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");// 可解决网页上中文乱码
        resp.getWriter().print(jsonObject.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String type = req.getParameter("type");
        String account = req.getParameter("account");
        JSONObject jsonObject = new JSONObject();
        switch (type) {
            case "getList":
                int page = Integer.parseInt(req.getParameter("page"));
                int page_count = Integer.parseInt(req.getParameter("page_count"));
                List<Dynamic> list = DBService.getService().getDynamicList(page, page_count);
                for (Dynamic d : list) {
                    int like_count = DBService.getService().getLikeCountForDId(d.getId());
                    int com_count = DBService.getService().getCommentCountForDId(d.getId());
                    d.setCommentCount(com_count);
                    d.setLikeCount(like_count);

                    UserBean user = DBService.getService().getUserBean(account);
                    if (user != null) {
                        boolean islike = DBService.getService().islikeDyForUId(d.getId(), user.getId());
                        d.setIsLike(islike ? 1 : 0);
                    } else {
                        d.setIsLike(0);
                    }
                }
                jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                jsonObject.put(Constant.KEY_LIST, list);
                jsonObject.put(Constant.KEY_MESSAGE, "");
                break;
            case "getDetail":
                break;
            case "getLikeList":

                UserBean user = DBService.getService().getUserBean(account);
                if (user != null) {
                    List<LikeBean> LikeList = DBService.getService().getLikeListForU(user.getId());
                    jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                    jsonObject.put(Constant.KEY_LIST, LikeList);
                    jsonObject.put(Constant.KEY_MESSAGE, "");
                } else {
                    jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_EMPTY_USER);
                    jsonObject.put(Constant.KEY_MESSAGE, Constant.Msg.ERROR_EMPTY_USER);
                }
                break;
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");// 可解决网页上中文乱码
        resp.getWriter().print(jsonObject.toString());
    }
}
