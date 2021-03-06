package com.sunn.crazy.servlet;

import com.sunn.crazy.Constant;
import com.sunn.crazy.bean.MusicBean;
import com.sunn.crazy.bean.TaskBean;
import com.sunn.crazy.service.DBService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/query_alarm")
public class QueryAlarmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        req.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");
        JSONObject jsonObject = new JSONObject();
        switch (type) {
            case "ringtone":
                List<MusicBean> musicList = DBService.getService().getMusicList();
                jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                jsonObject.put(Constant.KEY_LIST, musicList);
                jsonObject.put(Constant.KEY_MESSAGE, "");
                break;
            case "task":
                List<TaskBean> taskList = DBService.getService().getTaskList();
                jsonObject.put(Constant.KEY_RESULT, Constant.SUCCESS);
                jsonObject.put(Constant.KEY_LIST, taskList);
                jsonObject.put(Constant.KEY_MESSAGE, "");
                break;
            default:
                jsonObject.put(Constant.KEY_RESULT, Constant.Error.ERROR_NULL_PARAM);
                jsonObject.put(Constant.KEY_LIST, null);
                jsonObject.put(Constant.KEY_MESSAGE, "没有这个类型！");
                break;
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");// 可解决网页上中文乱码
        resp.getWriter().print(jsonObject.toString());
    }
}
