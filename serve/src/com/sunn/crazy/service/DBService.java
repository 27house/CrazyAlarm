package com.sunn.crazy.service;

import com.sunn.crazy.bean.*;
import com.sunn.crazy.utils.TextUtils;
import org.omg.DynamicAny.DynAny;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DBService {
    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String DB_NAME = "crazy";
    // JDBC 驱动名及数据库 URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static DBService service;

    public static DBService getService() {
        return new DBService();
    }

    private Connection conn = null;

    private DBService() {
        // 打开一个连接
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserBean getUserBean(String acc) {

        // 执行 SQL 查询
        Statement stmt = null;
        UserBean userBean = null;
        try {
            stmt = conn.createStatement();
            String sql;

            sql = "select * from user_info where account = '" + acc + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                userBean = new UserBean();
                userBean.setId(rs.getLong("id"));
                userBean.setAccount(rs.getString("account"));
                userBean.setNickname(rs.getString("nickname"));
                userBean.setPassword(rs.getString("password"));
                userBean.setAvatar(rs.getString("avatar"));
                userBean.setCreate_time(rs.getString("create_time"));
                userBean.setLogin_time(rs.getString("login_time"));
                userBean.setMotto(rs.getString("motto"));
                userBean.setSex(rs.getString("sex"));
            }

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return userBean;
    }

    public UserBean getUserBean(int userId) {

        // 执行 SQL 查询
        Statement stmt = null;
        UserBean userBean = null;
        try {
            stmt = conn.createStatement();
            String sql;

            sql = "select * from user_info where id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                userBean = new UserBean();
                userBean.setId(rs.getLong("id"));
                userBean.setAccount(rs.getString("account"));
                userBean.setNickname(rs.getString("nickname"));
                userBean.setAvatar(rs.getString("avatar"));
                userBean.setMotto(rs.getString("motto"));
                userBean.setSex(rs.getString("sex"));
            }

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return userBean;
    }

    public boolean putUserBean(String account, String password) {
        // 执行 SQL 查询
        Statement stmt = null;
        boolean rs;
        try {
            stmt = conn.createStatement();
            String nickname = "用户" + (int) (Math.random() * 9000) + 1000;
            String sql = "insert into user_info (account, password, nickname) values ('"
                    + account + "', '" + password + "', '" + nickname + "')";
            stmt.executeUpdate(sql);
            rs = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rs;
    }

    public boolean updateUser(String account, String key, String value) {
        // 执行 SQL 查询
        Statement stmt = null;
        boolean rs;
        try {
            stmt = conn.createStatement();
            String sql = "update user_info set " + key + " = '" + value + "' where account = '" + account + "' ";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rs = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rs;
    }

    public List<MusicBean> getMusicList() {
        List<MusicBean> list = new ArrayList<>();
        // 执行 SQL 查询
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql;

            sql = "select * from music where status = 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                MusicBean bean = new MusicBean();
                bean.setId(rs.getInt("id"));
                bean.setTitle(rs.getString("title"));
                bean.setDesc(rs.getString("motto"));
                bean.setUrl("http://192.168.0.136/admin/upload/music/" + rs.getString("url"));
                bean.setDownload_count(rs.getInt("download_count"));
                bean.setStatus(rs.getInt("status"));
                list.add(bean);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return list;
    }

    public List<TaskBean> getTaskList() {
        List<TaskBean> list = new ArrayList<>();
        // 执行 SQL 查询
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql = "select * from task where status = 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                TaskBean bean = new TaskBean();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                bean.setType(rs.getInt("type"));
                bean.setRules(rs.getString("rules"));
                list.add(bean);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return list;
    }

    public boolean putDynamic(long id, HttpServletRequest req, HttpServlet servlet) {
        // 执行 SQL 查询
        Statement stmt = null;
        boolean rs;
        try {
            stmt = conn.createStatement();
            String create_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            String pics = "";
            List<Part> partList = (List<Part>) req.getParts();
            for (Part part : partList) {
                if (part.getName().equals("file")) {
                    String showFile = DBService.getService().saveFile(req, part, servlet);
                    pics = pics + showFile + ",";
                }
            }
            if (!TextUtils.isEmpty(pics)) {
                pics = pics.substring(0, pics.length() - 1);
            }
            String sql = "INSERT INTO `crazy`.`dynamic` (`userId`, `content`, `create_time`,  `pics`) " +
                    "VALUES (" + id + ", '" + req.getParameter("content") + "', '" + create_time + "', '" + pics + "');";
            stmt.executeUpdate(sql);
            rs = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rs;
    }

    public List<Dynamic> getDynamicList(int page, int page_count) {
        List<Dynamic> list = new ArrayList<>();
        // 执行 SQL 查询
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql;
            int start = page * page_count;
            int end = start + page_count;
            sql = "SELECT dynamic . * , user_info.account, user_info.nickname, user_info.avatar, user_info.sex, user_info.motto" +
                    " FROM dynamic " +
                    " INNER JOIN user_info ON dynamic.userId = user_info.id" +
                    " ORDER BY dynamic.create_time desc,dynamic.id asc " +
                    " LIMIT " + start + " , " + end + "";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                Dynamic bean = new Dynamic();
                bean.setId(rs.getInt("id"));
                int userId = rs.getInt("userId");
                UserBean userBean = new UserBean();
                userBean.setId(userId);
                userBean.setAccount(rs.getString("account"));
                userBean.setNickname(rs.getString("nickname"));
                userBean.setMotto(rs.getString("motto"));
                userBean.setAvatar(rs.getString("avatar"));
                userBean.setSex(rs.getString("sex"));
                bean.setUser(userBean);
                bean.setContent(rs.getString("content"));
                bean.setCreate_time(rs.getString("create_time"));
                String pics = rs.getString("pics");
                if (TextUtils.isEmpty(pics)) {
                    bean.setPics(new ArrayList<>());
                } else {
                    String[] picArr = pics.split(",");
                    bean.setPics(new ArrayList<>(Arrays.asList(picArr)));
                }
                list.add(bean);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return list;
    }

    public String saveFile(HttpServletRequest req, Part part, HttpServlet servlet) throws IOException, ServletException {
        String inputName = part.getName();
        InputStream input = part.getInputStream();
        //想要保存的目标文件的目录下
        String tagDir = servlet.getServletContext().getRealPath("/upload");
        File tagDirFile = new File(tagDir);
        //判断上传文件的保存目录是否存在
        if (!tagDirFile.exists() && !tagDirFile.isDirectory()) {
            System.out.println(tagDir + "目录不存在，需要创建");
            //创建目录
            tagDirFile.mkdir();
        }
        //避免文件名重复使用uuid来避免,产生一个随机的uuid字符
        String realFileName = UUID.randomUUID().toString() + ".jpg";
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
        return showFile;
    }

    public boolean putLikeDy(long uId, String dId, String cId) {
        // 执行 SQL 查询
        Statement stmt = null;
        boolean rs;
        try {
            stmt = conn.createStatement();
            String nickname = "用户" + (int) (Math.random() * 9000) + 1000;
            String sql = "insert into dc_like (userId, dynamicId, commentId) values ('"
                    + uId + "', '" + dId + "', '" + cId + "')";
            stmt.executeUpdate(sql);
            rs = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rs;
    }

    public Dynamic getDynamicDetail(String dId) {

        // 执行 SQL 查询
        Statement stmt = null;
        Dynamic bean = null;
        try {
            stmt = conn.createStatement();
            String sql;

            sql = "SELECT dynamic.* , user_info.account, user_info.nickname, user_info.avatar, user_info.sex, user_info.motto" +
                    " FROM dynamic " +
                    " INNER JOIN user_info ON dynamic.userId = user_info.id" +
                    " ORDER BY dynamic.create_time desc,dynamic.id asc " +
                    " WHERE id = " + dId +
                    " LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                bean = new Dynamic();
                bean.setId(rs.getInt("id"));
                int userId = rs.getInt("userId");
                UserBean userBean = new UserBean();
                userBean.setId(userId);
                userBean.setAccount(rs.getString("account"));
                userBean.setNickname(rs.getString("nickname"));
                userBean.setMotto(rs.getString("motto"));
                userBean.setAvatar(rs.getString("avatar"));
                userBean.setSex(rs.getString("sex"));
                bean.setUser(userBean);
                bean.setContent(rs.getString("content"));
                bean.setCreate_time(rs.getString("create_time"));
                String pics = rs.getString("pics");
                if (TextUtils.isEmpty(pics)) {
                    bean.setPics(new ArrayList<>());
                } else {
                    String[] picArr = pics.split(",");
                    bean.setPics(new ArrayList<>(Arrays.asList(picArr)));
                }
            }

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return bean;
    }

    public boolean updateDynamic(String dId, String key, String value) {
        // 执行 SQL 查询
        Statement stmt = null;
        boolean rs;
        try {
            stmt = conn.createStatement();
            String sql = "update dynamic set " + key + " = '" + value + "' where id = '" + dId + "' ";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rs = true;
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rs;
    }

    public int getCommentCountForDId(int id) {
        Statement stmt = null;
        int count = 0;
        try {
            stmt = conn.createStatement();
            String sql = "select count(id) from comment where dynamicId = " + id;
            System.out.println(sql);
            ResultSet set = stmt.executeQuery(sql);
            set.next();
            count = set.getInt(1);
            set.close();
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return count;
    }

    public int getLikeCountForDId(int id) {
        Statement stmt = null;
        int count = 0;
        try {
            stmt = conn.createStatement();
            String sql = "select count(id) from dc_like where dynamicId = " + id;
            System.out.println(sql);
            ResultSet set = stmt.executeQuery(sql);
            set.next();
            count = set.getInt(1);
            set.close();
            // 完成后关闭
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return count;
    }

    public List<LikeBean> getLikeListForU(long id) {
        List<LikeBean> list = new ArrayList<>();
        return list;
    }

    public boolean islikeDyForUId(int d_id, long u_id) {
        // 执行 SQL 查询
        Statement stmt = null;
        boolean isLike = false;
        try {
            stmt = conn.createStatement();
            String sql;

            sql = "select * from dc_like where userId = " + u_id + " and dynamicId = " + d_id + " limit 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 通过字段检索
                isLike = true;
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return isLike;
    }

    public void removeLikeDy(long id, String dId, String cId) {
        // 执行 SQL 查询
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from dc_like where userId = " + id + " and dynamicId = " + dId;
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void removeDynamic(long id, String dId) {
        // 执行 SQL 查询
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "delete from dynamic where userId = " + id + " and id = " + dId;
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
