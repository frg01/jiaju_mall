package com.hspedu.furns.web;

import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;
import com.hspedu.furns.service.impl.MemberServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends BasicServlet {
    private MemberService memberService = new MemberServiceImp();
    //管理员登录
    public void managerLogin(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
//        System.out.println("memberServlet被调用。。。");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Member member = new Member(null, username, password, null);
        Member member1 = memberService.managerLogin(member);
        if (member1 != null) {
            //管理员存在
//            System.out.println("存在" + member1);
            request.getSession().setAttribute("member",member1);
            request.getRequestDispatcher("views/manage/manage_menu.jsp").forward(request, response);
        } else {
            //不存在
//            System.out.println("不存在" + member1);
            request.setAttribute("msg", "登录名或者密码输入有误");
            request.setAttribute("username", member.getUsername());
            request.getRequestDispatcher("views/manage/manage_login.jsp").forward(request, response);
        }
    }
}
