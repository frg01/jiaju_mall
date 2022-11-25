//package com.hspedu.furns.web;
//
//import com.hspedu.furns.entity.Member;
//import com.hspedu.furns.service.MemberService;
//import com.hspedu.furns.service.impl.MemberServiceImp;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class RegisterServlet extends HttpServlet {
//    //定义一个属性MemberService
//    private MemberService memberService = new MemberServiceImp();
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("RegisterServlet被调用....");
//        //接收用户注册信息
//        /*
//         <input type="text" id="username" name="username" placeholder="Username"/>
//         <input type="password" id="password" name="password" placeholder="输入密码"/>
//         <input type="password" id="repwd" name="repwd" placeholder="确认密码"/>
//         <input name="email" id="email" placeholder="电子邮件" type="email"/>
//         */
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String email = request.getParameter("email");
//
//        //判断用户名是不是可用的
//        if (!memberService.isExistUsername(username)) {
//            //注册
//            System.out.println("用户名可用，数据库中不存在" + username);
//            Member member = new Member(null, username, password, email);
//            //输入的注册信息存入数据库中
//            if (memberService.registerMember(member)){
//                //请求转发
//                request.getRequestDispatcher("/views/member/register_ok.jsp").forward(request,response);
//            }else{
//                request.getRequestDispatcher("/views/member/register_fail.jsp").forward(request,response);
//            }
//        }else{
//            //返回注册页面
//            request.getRequestDispatcher("/views/member/login.jsp").forward(request,response);
//        }
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//doPost(request,response);
//    }
//}
