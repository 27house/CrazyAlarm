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
        req.setCharacterEncoding("UTF-8");
        String account = req.getParameter("account");
        Part part = req.getPart("file");
        JSONObject jsonObject = new JSONObject();
        if (!TextUtils.isEmpty(account)) {
            String inputName = part.getName();
            InputStream input = part.getInputStream();
            //想要保存的目标文件的目录下
            String tagDir = getServletContext().getRealPath("/upload");
            File tagDirFile = new File(tagDir);
            //判断上传文件的保存目录是否存在
            if (!tagDirFile.exists() && !tagDirFile.isDirectory()) {
                System.out.println(tagDir + "目录不存在，需要创建");
                //创建目录
                tagDirFile.mkdir();
            }
            //避免文件名重复使用uuid来避免,产生一个随机的uuid字符
            String realFileName = UUID.randomUUID().toString();
            OutputStream output = new FileOutputStream(new File(tagDir, realFileName));
            int len = 0;
            byte[] buff = new byte[1024 * 8];
            while ((len = input.read(buff)) > -1) {
                output.write(buff, 0, len);
            }
            input.close();
            output.close();
            String path = tagDir + "/" + realFileName;
            String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
            String showFile = basePath + "/upload/" + realFileName;
            boolean result = DBService.getService().updateUser(account, "avatar", showFile);
            if (result) {
                jsonObject.put("result", Constant.SUCCESS);
                jsonObject.put("message", Constant.Msg.SUCCESS_UPLOAD);
            } else {
                jsonObject.put("result", Constant.Error.ERROR_DB);
                jsonObject.put("message", Constant.Msg.ERROR_UPLOAD);
                File file = new File(path);
                file.delete();
            }
        } else {
            jsonObject.put("result", Constant.Error.ERROR_NULL_PARAM);
            jsonObject.put("message", Constant.Msg.ERROR_NULL_PARAM);
        }
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print(jsonObject.toString());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
