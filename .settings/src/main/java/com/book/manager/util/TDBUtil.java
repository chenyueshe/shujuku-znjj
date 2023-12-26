package com.book.manager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TDBUtil {
	 private static String driverClass = "com.teradata.jdbc.TeraDriver";
	    private static String url = "jdbc:teradata://10.220.8.60/CLIENT_CHARSET=cp936,TMODE=TERA,CHARSET=ASCII,DATABASE=NMART";
	    private static String user = "tetl";
	    private static String pwd = "nmbi_tetl_lsc001";

	    public static Connection getConnection() {
	        Connection connection = null;
	        // 1.注册驱动
	        try {
	            Class.forName(driverClass);

	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            System.out.println("数据库驱动异常");
	        }

	        // 2.得到连接
	        try {
	        	if(connection!=null) {
	        		return connection;
	        	}else {
	        		System.out.println("正在获取新TD数据库的连接");
	        		return  connection = DriverManager.getConnection(url, user, pwd);
	        	}
	           
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            System.out.println("数据库连接异常");
	        }
	        return connection;
	    }


	    public  static void closeAll(ResultSet resultSet,Statement  statement,   Connection connection) {
	          if (resultSet!=null) {
	              try {
	                resultSet.close();
	            } catch (SQLException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        }
	          if (statement!=null) {
	              try {
	                  statement.close();
	            } catch (SQLException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        }
	          if (connection!=null) {
	              try {
	                 connection.close();
	            } catch (SQLException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        }
	    }
}

