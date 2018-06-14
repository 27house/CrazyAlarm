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

@WebServlet("/register")
public class UserRegister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
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
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
            UserBean user = DBService.getService().getUserBean(account);
            if (user == null) {
                boolean rs = DBService.getService().putUserBean(account, password);
                if (rs) {
                    // 注册成功
                    jsonObject.put("result", Constant.SUCCESS);
                    jsonObject.put("message", Constant.Msg.SUCCESS_REGISTER);
                } else {
                    // 写入数据库失败
                    jsonObject.put("result", Constant.Error.ERROR_DB);
                    jsonObject.put("message", Constant.Msg.ERROR_REGISTER);
                }
            } else {
                // 已注册
                jsonObject.put("result", Constant.Error.ERROR_EXIT_USER);
                jsonObject.put("message", Constant.Msg.ERROR_EXIT_USER);
            }
        } else {
            jsonObject.put("result", Constant.Error.ERROR_NULL_PARAM);
            jsonObject.put("message", Constant.Msg.ERROR_NULL_PARAM);
            // 用户名密码空
        }
        out.write(jsonObject.toString());
        out.flush();
    }
}
