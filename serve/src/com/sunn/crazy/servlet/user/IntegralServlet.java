package com.sunn.crazy.servlet.user;

import com.sunn.crazy.Constant;
import com.sunn.crazy.bean.UserBean;
import com.sunn.crazy.service.DBService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/integral")
public class IntegralServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        String account = req.getParameter("account");
        UserBean userBean = DBService.getService().getUserBean(account);
        JSONObject jsonObject = new JSONObject();
        if (userBean != null) {
            switch (type) {
                case "get":
                    int integral = DBService.getService().getUserIntegral(userBean.getId());
                    jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                    jsonObject.put(Constant.KEY_DATA, integral);
                    jsonObject.put(Constant.KEY_MESSAGE, "");
                    break;
                case "get_sign":
                    String year = req.getParameter("year");
                    String month = req.getParameter("month");
                    List<String> dates = DBService.getService().getSignDates(userBean.getId(), year, month);
                    jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                    jsonObject.put(Constant.KEY_LIST, dates);
                    jsonObject.put(Constant.KEY_MESSAGE, "");
                    break;
            }
        } else {
            jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_EMPTY_USER);
            jsonObject.put(Constant.KEY_MESSAGE, Constant.Msg.ERROR_EMPTY_USER);
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(jsonObject.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String type = req.getParameter("type");
        String account = req.getParameter("account");
        UserBean userBean = DBService.getService().getUserBean(account);
        JSONObject jsonObject = new JSONObject();
        if (userBean != null) {
            switch (type) {
                case "sign":
                    String date = req.getParameter("date");//20180619
                    if (date.length() >= 8) {
                        String year = date.substring(0, 4);
                        String month = date.substring(4, 6);
                        String day = date.substring(6, 8);
                        String status = req.getParameter("status");
                        List<String> dates = DBService.getService().getSignDates(userBean.getId(), year, month);
                        if (!dates.contains(date)) {
                            boolean result = DBService.getService().signIn(userBean.getId(), year, month, day, status);
                            if (result) {
                                int integral = DBService.getService().getUserIntegral(userBean.getId());
                                integral += 10;
                                result = DBService.getService().updateIntegral(userBean.getId(), integral);
                                if (result) {
                                    jsonObject.put("result", Constant.SUCCESS);
                                    jsonObject.put("message", "签到成功！");
                                    break;
                                }
                            }
                        } else {
                            jsonObject.put("result", Constant.Error.ERROR_EXIT_USER);
                            jsonObject.put("message", "已经签到过了！");
                            break;
                        }
                    }
                    jsonObject.put("result", Constant.Error.ERROR_DB);
                    jsonObject.put("message", "签到失败！");
                    break;
            }
        } else {
            jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_EMPTY_USER);
            jsonObject.put(Constant.KEY_MESSAGE, Constant.Msg.ERROR_EMPTY_USER);
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(jsonObject.toString());

    }
}
