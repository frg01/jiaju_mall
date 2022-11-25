package com.hspedu.furns.test;

import com.hspedu.furns.utils.JDBCUtilsByDruid;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class JDBCUtilsByDruidTest {
    @Test
    public void getConnection() throws SQLException {
        //mysql,jdbc,数据连接池
        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println("connection = " + connection);
        JDBCUtilsByDruid.close(null,null,connection);
    }
}
