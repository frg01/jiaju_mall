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
//public class LoginServlet extends HttpServlet {
//    //定义一个属性MemberService
//    private MemberService memberService = new MemberServiceImp();
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("LoginServlet被调用。。。");
//        String loginUsername = request.getParameter("username");
//        String loginPassword = request.getParameter("password");
//        //将输入的用户名和密码存入Member对象
//        Member member = new Member(null, loginUsername, loginPassword, null);
//        if (memberService.login(member)){
//            //登录成功，请求转发 登录成功页面
//            System.out.println("登录成功");
//            request.getRequestDispatcher("/views/member/login_ok.jsp").forward(request,response);
//        }else{
//            //登陆失败，请求转发 登陆页面
//            System.out.println("登陆失败");
//            request.setAttribute("failure","用户名或密码不正确！");
//            request.setAttribute("username",member.getUsername());
//            request.getRequestDispatcher("/views/member/login.jsp").forward(request,response);
//        }
//
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//doPost(request,response);
//    }
//}
