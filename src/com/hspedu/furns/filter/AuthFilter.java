package com.hspedu.furns.filter;

import com.google.gson.Gson;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限验证过滤器，对指定的url进行校验，登陆过才放行，不然去登录
 *
 * @author: guorui fu
 * @versiion: 1.0
 */
public class AuthFilter implements Filter {//选择servlet的filter
    //压迫排除的url放入List
    private List<String> excludedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取要配置排除的url
        String strExcludedUrls = filterConfig.getInitParameter("excludedUrls");
        String[] splitUrl = strExcludedUrls.split(",");
        //将splitUrl 转成List
        excludedUrls = Arrays.asList(splitUrl);


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //请求的url
        String url = request.getServletPath();

        //判断是否要验证
        if (!excludedUrls.contains(url)) {
            Member member = (Member) request.getSession().getAttribute("member");
            String username = member.getUsername();
            String password = member.getPassword();
            System.out.println("username" + username + "密码" + password);
            //没有登录member为null
            if (member == null) {
                //判断是不是ajax请求
                if (!WebUtils.isAjaxRequest(request)){
                    request.getRequestDispatcher("/views/member/login.jsp").forward(servletRequest, servletResponse);
                }else{//如果是
                    //返回一个url  按照json格式返回
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("url","views/member/login.jsp");
                    String resultJson = new Gson().toJson(resultMap);

                    servletResponse.getWriter().write(resultJson);
                }
                return;
            }  else if (!"admin".equals(member.getUsername())){
                System.out.println("密码或用户名不是admin");
                //如果member不为null,还应当判断该member是不是admin,
                //在根据获取到的url进行相应处理，不是admin访问，转到首页
                if (url.contains("/manage/furnServlet") || url.contains("/views/manage")){
                    request.getRequestDispatcher("/index.jsp").forward(servletRequest,servletResponse);
                }
            }
        }

        //通过过滤器
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
