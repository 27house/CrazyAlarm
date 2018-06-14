package com.sunn.crazy.service;

import com.sunn.crazy.bean.MusicBean;
import com.sunn.crazy.bean.TaskBean;
import com.sunn.crazy.bean.UserBean;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
                userBean.setDescribe(rs.getString("describe"));
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
                bean.setDesc(rs.getString("desc"));
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

    public boolean putDynamic(long id, HttpServletRequest req) {
        // 执行 SQL 查询
        Statement stmt = null;
        boolean rs;
        try {
            stmt = conn.createStatement();
            String create_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            String sql = "INSERT INTO `crazy`.`dynamic` (`userId`, `content`, `create_time`,  `pics`) " +
                    "VALUES (" + id + ", '" + req.getParameter("content") + "', '" + create_time + "', '" + req.getParameter("pics") + "');";
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
}
