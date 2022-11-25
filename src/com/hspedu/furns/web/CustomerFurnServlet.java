package com.hspedu.furns.web;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CustomerFurnServlet extends BasicServlet {
    FurnService furnService = new FurnServiceImpl();
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //与后台显示家居逻辑类似
        int pageNum = DataUtils.parseInt(request.getParameter("pageNum"), 1);
        int pageSize = DataUtils.parseInt(request.getParameter("pageSize"), 8);
        //调用furnService,获取page对象
        Page<Furn> page = furnService.page(pageNum,pageSize);
        page.setUrl("customerFurnServlet?pageNum=");
        //放入request域
        request.setAttribute("page",page);
        //请求转发
        request.getRequestDispatcher("/views/customer/index.jsp").forward(request,response);
    }


    //通过名称搜索
    protected void pageByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNum = DataUtils.parseInt(request.getParameter("pageNum"),1);
        int pageSize = DataUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        //如果参数有name 但没有值，接收到的是""
        //如果name参数都没有，接收到的是null,这时null -> "",使它默认能够显示全部数据
        //把"" 和 null 的业务逻辑合并在一起了
        String name = request.getParameter("name");
        if (name == null){
            name = "";
        }

        Page<Furn> page = furnService.pageByName(pageNum, pageSize, name);
        //设置page的url
        StringBuilder url = new StringBuilder("customerFurnServlet?action=pageByName");
        if (!"".equals(name)){//如果name不为""，则给url拼接name
            url.append("&name=").append(name);
        }
        page.setUrl(url.toString());
        request.setAttribute("page",page);
        request.getRequestDispatcher("/views/customer/index.jsp").forward(request,response);
    }
}
