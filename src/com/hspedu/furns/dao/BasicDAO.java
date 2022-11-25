package com.hspedu.furns.dao;



import com.hspedu.furns.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author fgr
 * @version 1.0
 * 开发BasicDAO , 是其他DAO的父类
 */
public class BasicDAO<T> { //泛型指定具体类型

    private QueryRunner qr =  new QueryRunner();

    //开发通用的dml方法, 针对任意的表
    public int update(String sql, Object... parameters) {

        Connection connection = null;

        try {
            //从数据库连接池获取了connection
            //每次从连接池中取出connection 不能保证是同一个
            //从threadLocal取出的connection，可以保证是同一个连接（同一个线程中/同一个请求中）
            connection = JDBCUtilsByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);
            return update;
        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出

        }
    }

    //返回多个对象(即查询的结果是多行), 针对任意表
    /**
     *
     * @param sql sql 语句，可以有 ?
     * @param clazz 传入一个类的Class对象 比如 Actor.class
     * @param parameters 传入 ? 的具体的值，可以是多个
     * @return 根据Actor.class 返回对应的 ArrayList 集合
     */
    public List<T> queryMulti(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出


        }
    }

    //查询单行结果 的通用方法
    public T querySingle(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出

        }
    }

    //查询单行单列的方法,即返回单值的方法
    public Object queryScalar(String sql, Object... parameters) {

        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler(), parameters);

        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出

        }
    }

    //查询某张表中有多少的数据
//    public int tableTotalRow(String sql,Class<T> clazz){
//        Connection connection = null;
//        try {
//            connection = JDBCUtilsByDruid.getConnection();
//            T query = qr.query(sql);
//        } catch (SQLException e) {
//            throw new RuntimeException();
//        }
//    }

}
