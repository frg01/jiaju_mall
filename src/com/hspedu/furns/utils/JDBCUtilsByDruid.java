package com.hspedu.furns.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 韩顺平
 * @version 1.0
 * 基于druid数据库连接池的工具类
 */
public class JDBCUtilsByDruid {

    private static DataSource ds;
    //定义ThreadLocal  存放一个Connection
    private static ThreadLocal<Connection> threadLocalConn = new ThreadLocal<>();

    //在静态代码块完成 ds初始化
    static {
        Properties properties = new Properties();
        try {
            //因为我们是web项目，他的工作目录在out，文件的加载需要使用类加载器
            //要找到我们的工作目录
//            properties.load(new FileInputStream("src\\druid.properties"));
            properties.load(JDBCUtilsByDruid.class.getClassLoader()
                    .getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //编写getConnection方法
//    public static Connection getConnection() throws SQLException {
//        return ds.getConnection();
//    }

    /**
     * 从ThreadLocal获取connection，保证同一个线程中，获取的是同一个connection
     * @return
     * @throws SQLException
     */
    public static Connection getConnection()  {
        Connection connection = threadLocalConn.get();
        if (connection == null){//threadLocalConn没有连接
            //从数据库连接池中，取出一个连接放入threadLocalConn
            try {
                connection = ds.getConnection();
                //将连接设置为手动提交
                connection.setAutoCommit(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            threadLocalConn.set(connection);
        }
        return connection;
    }

    //提交事务，mysql事务，线程，过滤机制，ThreadLocal
    public static void commit(){
        Connection connection = threadLocalConn.get();
        if (connection != null){//确保该连接非空
            try {
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();//连接释放，给回连接池
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            //提交后需要把connection从threadLocalConn清楚，不然会造成threadLocal长期持有connection
            //同时tomcat底层使用的是线程池技术
            threadLocalConn.remove();
        }
    }

    //撤销connection管理的操作dml
    public static void rollBack(){
        Connection connection = threadLocalConn.get();
        if (connection != null){//保证当前连接有效
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        threadLocalConn.remove();
    }

    //关闭连接, 老师再次强调： 在数据库连接池技术中，close 不是真的断掉连接
    //而是把使用的Connection对象放回连接池
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
