package com.sunn.crazy.servlet;

import com.sunn.crazy.bean.UserBean;
import com.sunn.crazy.service.DBService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        // 设置字符编码为UTF-8, 这样支持汉字显示
        resp.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        resp.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST");
        JSONObject jsonObject = new JSONObject();
        Writer out = resp.getWriter();
        // 登录
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        if (account == null || "".equals(account.trim()) || password == null || "".equals(password.trim())) {
            System.out.println("用户名或密码不能为空！");
            jsonObject.put("result", -2);
            jsonObject.put("data", null);
            jsonObject.put("message", "用户名或密码不能为空！");
        } else {
            UserBean userBean = DBService.getService().getUserBean(account);
            //将java对象转换为json对象
            if (userBean != null && userBean.getPassword().equals(password)) {
                jsonObject.put("result", 0);
                jsonObject.put("data", userBean);
                jsonObject.put("message", "用户登录成功！");
                System.out.println("登录成功！");
            } else {
                System.out.println("用户名或密码错误，登录失败！");
                jsonObject.put("result", -1);
                jsonObject.put("data", null);
                jsonObject.put("message", "用户登录失败！");
            }
        }
        out.write(jsonObject.toString());
        out.flush();
    }
}
