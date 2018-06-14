package com.sunn.crazy.servlet.user;

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

@WebServlet("/getInfo")
public class GetUserInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        req.setCharacterEncoding("UTF-8");
        String account = req.getParameter("account");
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(account)) {
            UserBean userBean = DBService.getService().getUserBean(account);
            //将java对象转换为json对象
            if (userBean != null) {
                jsonObject.put("result", Constant.SUCCESS);
                jsonObject.put("data", userBean);
                jsonObject.put("message", "");
            } else {
                jsonObject.put("result", Constant.Error.ERROR_EMPTY_USER);
                jsonObject.put("message", Constant.Msg.ERROR_EMPTY_USER);
            }
        } else {
            jsonObject.put("result", Constant.Error.ERROR_NULL_PARAM);
            jsonObject.put("message", Constant.Msg.ERROR_NULL_PARAM);
        }
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print(jsonObject.toString());
    }
}
