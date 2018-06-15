package com.sunn.crazy.servlet;

import com.sunn.crazy.Constant;
import com.sunn.crazy.bean.Dynamic;
import com.sunn.crazy.bean.UserBean;
import com.sunn.crazy.service.DBService;
import com.sunn.crazy.utils.TextUtils;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dynamic")
public class DynamicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String type = req.getParameter("type");
        String account = req.getParameter("account");
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(account)) {
            UserBean user = DBService.getService().getUserBean(req.getParameter("account"));
            if (user != null) {
                switch (type) {
                    case "add_dynamic":
                        boolean success = DBService.getService().putDynamic(user.getId(), req);
                        if (success) {
                            jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                            jsonObject.put(Constant.KEY_MESSAGE, "发布成功！");
                        } else {
                            jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_DB);
                            jsonObject.put(Constant.KEY_MESSAGE, "发布失败！");
                        }
                        break;
                    case "delete_dynamic":
                        break;
                    case "add_like":
                        break;
                    case "delete_like":
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
        JSONObject jsonObject = new JSONObject();
        switch (type) {
            case "getList":
                int page = Integer.parseInt(req.getParameter("page"));
                int page_count = Integer.parseInt(req.getParameter("page_count"));
                List<Dynamic> list = DBService.getService().getDynamicList(page, page_count);
                jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                jsonObject.put(Constant.KEY_LIST, list);
                jsonObject.put(Constant.KEY_MESSAGE, "");
                break;
            case "getDetail":
                break;
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");// 可解决网页上中文乱码
        resp.getWriter().print(jsonObject.toString());
    }
}
