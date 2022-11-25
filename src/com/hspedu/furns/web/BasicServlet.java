package com.hspedu.furns.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public abstract class BasicServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("BasicServlet的doPost方法");
        //解决接收到的数据中文乱码问题
        req.setCharacterEncoding("utf-8");
        //获取action
        String action = req.getParameter("action");

        //反射机制，调用子类中的方法
        /* action值要和方法名保持一致
        1.子类servlet继承了BasicServlet，所有请求会通过子类到父类找都doPost()
        2.父类的doPost()方法里动态的利用反射机制调用子类中的特有方法，减少了子类中的大量if-else语句
        3.抽象类设计模板，动态绑定，反射机制
         */
        try {
            Method declaredMethod = this.getClass().
                    getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            declaredMethod.invoke(this,req,resp);
        } catch (Exception e) {
            //将发生的异常继续抛出
            throw new RuntimeException(e);
        }
    }

    //增加处理Get请求

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
