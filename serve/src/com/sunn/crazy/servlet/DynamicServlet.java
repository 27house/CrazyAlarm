package com.sunn.crazy.servlet;

import com.sunn.crazy.Constant;
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

@WebServlet("/dynamic")
public class DynamicServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String account = req.getParameter("account");
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(account)) {
            String type = req.getParameter("type");
            UserBean user = DBService.getService().getUserBean(req.getParameter("account"));
            if (user != null) {
                switch (type) {
                    case "add_dynamic":
                        boolean success = DBService.getService().putDynamic(user.getId(), req);
                        if (success) {
                            jsonObject.put("result", Constant.SUCCESS);
                            jsonObject.put("message", "发布成功！");
                        } else {
                            jsonObject.put("result", Constant.Error.ERROR_DB);
                            jsonObject.put("message", "发布失败！");
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
                jsonObject.put("result", Constant.Error.ERROR_EMPTY_USER);
                jsonObject.put("message", Constant.Msg.ERROR_EMPTY_USER);
            }
        } else {
            jsonObject.put("result", Constant.Error.ERROR_NULL_PARAM);
            jsonObject.put("message", Constant.Msg.ERROR_NULL_PARAM);
        }
    }
}
