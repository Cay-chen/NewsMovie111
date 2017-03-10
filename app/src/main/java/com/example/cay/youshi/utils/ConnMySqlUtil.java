package com.example.cay.youshi.utils;

/**
 * rr
 * Created by cay on 16/7/20.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnMySqlUtil {
    final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String mysqlUrl = "jdbc:mysql://bdm259767417.my3w.com/bdm259767417_db?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true";
    private static final String username = "bdm259767417";//数据库用户名
    private static final String password = "czs0510016";//数据库 密码

    public static String getIp(String sql) {
        Connection conn = null;
        String ip = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(mysqlUrl, username, password);
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            System.out.println(result);
            while (result.next()) {
                ip = result.getString("ip");
            }
        } catch (ClassNotFoundException e) {
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return ip.trim();
    }

}