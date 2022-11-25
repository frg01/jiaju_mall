package com.hspedu.furns.web;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.gson.Gson;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;
import com.hspedu.furns.service.impl.MemberServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class MemberServlet extends BasicServlet {
    //定义一个属性MemberService
    private MemberService memberService = new MemberServiceImp();

    /**
     * 验证某个用户名是否已经存在db
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void isExistUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户名
        String username = req.getParameter("username");
        //调用service
        boolean isExistUsername = memberService.isExistUsername(username);
        //如何返回json格式，根据前端的需求
        //json使用拼接
//        String resultJson = "{\"isExist\":" + isExistUsername + "}";
        //
        //将要返回的数据 =》map =》json
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isExist",isExistUsername);
        Gson gson = new Gson();
        String resultJson = gson.toJson(resultMap);

        resp.getWriter().write(resultJson);
    }

    //验证码是否正确
    protected void isExistCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        boolean isExistCode;
        if (code != null && code.equalsIgnoreCase(token)){
            isExistCode = true;
        }else {
            isExistCode = false;
        }
        ////将要返回正确code的数据 =》map =》json
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("isExist",isExistCode);
        Gson gson = new Gson();
        String resultJson = gson.toJson(codeMap);
        resp.getWriter().write(resultJson);
    }

    /**
     * login请求 处理会员的登录
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("LoginServlet被调用。。。");
        String loginUsername = request.getParameter("username");
        String loginPassword = request.getParameter("password");
        //将输入的用户名和密码存入Member对象
        Member member = memberService.login(new Member(null, loginUsername, loginPassword, null));

        if (null != member) {
            //登录成功，请求转发 登录成功页面
            System.out.println("登录成功");
            //登陆成功，填写的name保存为属性
            request.getSession().setAttribute("member", member);
            request.getRequestDispatcher("/views/member/login_ok.jsp").forward(request, response);
        } else {
            //登陆失败，请求转发 登陆页面
            System.out.println("登陆失败");
            request.setAttribute("msg", "用户名或密码不正确！");
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        }

    }

    /**
     * register请求  处理会员的注册
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("RegisterServlet被调用....");
        //接收用户注册信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        //获取用户提交的验证码
        String code = request.getParameter("code");
        System.out.println("code=" + code);
        //从session获取验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        System.out.println("token=" + token);
        //立即删除，防止重复使用
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //如果token不为空（防止并发），并且与用户提交的验证码一致就继续
        if (token != null && code.equalsIgnoreCase(token)) {
            //判断用户名是不是可用的
            if (!memberService.isExistUsername(username)) {
                //注册
                System.out.println("用户名可用，数据库中不存在" + username);
                Member member = new Member(null, username, password, email);
                //输入的注册信息存入数据库中
                if (memberService.registerMember(member)) {
                    //请求转发
                    System.out.println("存入数据库");
                    request.getRequestDispatcher("/views/member/register_ok.jsp").forward(request, response);
                } else {
                    System.out.println("存入失败");
                    request.getRequestDispatcher("/views/member/register_fail.jsp").forward(request, response);
                }
            } else {
                //返回注册页面
                System.out.println("用户名不可用");
                request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
            }
        }else{//验证码不正确
            request.setAttribute("msg","验证码不正确");
            //回显数据
            request.setAttribute("username",username);
            request.setAttribute("email",email);
            //带回一个信息，显示到注册页面
            request.setAttribute("active","register");
            request.getRequestDispatcher("/views/member/login.jsp").forward(request,response);
        }

    }


    //退出登录
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //让当前用户的session销毁
        request.getSession().invalidate();
        //重新定向到首页
        response.sendRedirect(request.getContextPath());
    }
}
