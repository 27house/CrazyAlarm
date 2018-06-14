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
import java.io.Writer;

@WebServlet("/getUserInfo")
public class UserGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        JSONObject jsonObject = new JSONObject();
        // 登录
        String account = req.getParameter("account");
        if (account == null || "".equals(account.trim())) {
            jsonObject.put("result", -2);
            jsonObject.put("data", null);
            jsonObject.put("message", "用户名不能为空！");
        } else {
            UserBean userBean = DBService.getService().getUserBean(account);
            //将java对象转换为json对象
            if (userBean != null) {
                jsonObject.put("result", 0);
                jsonObject.put("data", userBean);
                jsonObject.put("message", "");
                System.out.println("登录成功！");
            } else {
                System.out.println("用户名或密码错误，登录失败！");
                jsonObject.put("result", -1);
                jsonObject.put("data", null);
                jsonObject.put("message", "");
            }
        }
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(jsonObject.toString());
    }
}
