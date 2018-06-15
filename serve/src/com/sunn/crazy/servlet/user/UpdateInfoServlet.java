package com.sunn.crazy.servlet.user;

import com.sunn.crazy.Constant;
import com.sunn.crazy.bean.UserBean;
import com.sunn.crazy.service.DBService;
import com.sunn.crazy.utils.TextUtils;
import net.sf.json.JSONObject;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateInfo")
@MultipartConfig
public class UpdateInfoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String account = req.getParameter("account");
        String type = req.getParameter("type");
        String value = req.getParameter("value");
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(account)) {
            String key;
            switch (type) {
                case "0":
                    key = "nickname";
                    break;
                case "1":
                    key = "sex";
                    break;
                case "2":
                    key = "password";
                    break;
                case "3":
                    key = "motto";
                    break;
                default:
                    key = "nickname";
                    break;
            }
            boolean result = DBService.getService().updateUser(account, key, value);
            if (result) {
                jsonObject.put("result", Constant.SUCCESS);
                jsonObject.put("message", Constant.Msg.SUCCESS_UPDATE);
            } else {
                jsonObject.put("result", Constant.Error.ERROR_DB);
                jsonObject.put("message", Constant.Msg.ERROR_UPDATE);
            }
        } else {
            jsonObject.put("result", Constant.Error.ERROR_NULL_PARAM);
            jsonObject.put("message", Constant.Msg.ERROR_NULL_PARAM);
        }
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print(jsonObject.toString());
    }
}
