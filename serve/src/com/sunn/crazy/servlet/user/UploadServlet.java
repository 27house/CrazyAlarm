package com.sunn.crazy.servlet.user;

import com.sunn.crazy.Constant;
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
import javax.servlet.http.Part;
import java.io.*;
import java.util.UUID;

@WebServlet("/uploadAvatar")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String account = req.getParameter("account");
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(account)) {
            String showFile = DBService.getService().saveFile(req, req.getPart("file"), this);
            boolean result = DBService.getService().updateUser(account, "avatar", showFile);
            if (result) {
                jsonObject.put("result", Constant.SUCCESS);
                jsonObject.put("message", Constant.Msg.SUCCESS_UPLOAD);
            } else {
                jsonObject.put("result", Constant.Error.ERROR_DB);
                jsonObject.put("message", Constant.Msg.ERROR_UPLOAD);
//                File file = new File(path);
//                file.delete();
            }
        } else {
            jsonObject.put("result", Constant.Error.ERROR_NULL_PARAM);
            jsonObject.put("message", Constant.Msg.ERROR_NULL_PARAM);
        }
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print(jsonObject.toString());
    }
}
