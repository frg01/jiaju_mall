package com.hspedu.furns.web;

import com.google.gson.Gson;
import com.hspedu.furns.entity.*;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import com.hspedu.furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class CartServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

//    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //从前端获取id
//        int id = DataUtils.parseInt(request.getParameter("id"), 0);
//        //通过id在数据库中找到furn
//        Furn furn = furnService.queryFurnById(id);
//        //todo
//        if (null == furn) {
//            return;
//        }
//        //根据furn构建CarItem
//        CartItem item = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());
//
//        //从session中获取cart对象
//        Cart cart = (Cart) request.getSession().getAttribute("cart");
//
//        if (null == cart) {//如果cart为空,就放new一个Cart放入
//            cart = new Cart();
//            request.getSession().setAttribute("cart", cart);
//        }
//
//        //添加到购物车
//        cart.addItem(item);
//        System.out.println(cart);
//
//        //添加完毕后，返回添加家居的页面 referer请求头中记录了点击添加购物车页面的地址参考路径
//        response.sendRedirect(request.getHeader("referer"));
//    }

    //在购物车中，通过id删除加购的商品
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到id
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        //得到pageNum
        int pageNum = DataUtils.parseInt(request.getParameter("pageNum"), 1);
        //拿到session中的cart的items,通过id删除items对应cartItem对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (null != cart) {
            cart.deleteItem(id);
        }
        System.out.println(cart.getItems());

        request.setAttribute("msg", "删除成功");
        request.getRequestDispatcher("cartServlet?action=page&pageNum=" + pageNum).forward(request, response);
    }

    //修改购物车中cartItem的数量
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        //这里可以根据业务处理如果count格式不正确就不继续进行
        int count = DataUtils.parseInt(request.getParameter("count"), 1);

        //拿到session中的cart
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (null != cart) {
            cart.updateCount(id, count);
        }
        //回到请求更新购物车的页面 header中有个记录请求发出的原页面的地址
        response.sendRedirect(request.getHeader("Referer"));

    }

    //清空购物车
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (null != cart) {
            cart.clear();
        }
        response.sendRedirect(request.getContextPath() + "/views/customer/index.jsp");
    }

    //购物车page
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNum = DataUtils.parseInt(req.getParameter("pageNum"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //取出session中的cart
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //创建一个List用来放cartItem
        List<CartItem> cartItems = new ArrayList<>();

        //循环取出放入
        Set<Integer> keys = cart.getItems().keySet();
        int j = pageNum * pageSize;
        int i = (pageNum - 1) * pageSize;
        int count = 0;
        for (Integer key : keys) {
            CartItem cartItem = cart.getItems().get(key);
            if (count >= i && count < j){
                cartItems.add(cartItem);
            }
            count++;
        }

        //计算出总页数
        int pageTotalCount = cart.getItems().size() / pageSize;
        if (cart.getItems().size() % pageSize != 0) {
            pageTotalCount++;
        }
        //购物车总行数
        int totalRow = cart.getItems().size();
        //路径
        String url = "cartServlet?action=page";
        //创建page对象
        Page<CartItem> page = new Page<>(pageNum, pageSize, cartItems, pageTotalCount, totalRow, url);

        //请求转发
        req.setAttribute("page", page);
        req.getRequestDispatcher("views/cart/cart.jsp").forward(req, resp);
    }

    //利用ajax  添加购物车后刷新首页购物车数量
    protected void addItemByAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //从前端获取id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //通过id在数据库中找到furn
        Furn furn = furnService.queryFurnById(id);
        //todo
//        if (null == furn) {
//            return;
//        }
        //根据furn构建CarItem
        CartItem item = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        //从session中获取cart对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (null == cart) {//如果cart为空,就放new一个Cart放入
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        //添加到购物车
        cart.addItem(item);

        //转json数据返回购物车里的数量
        int totalCount = cart.getTotalCount();
        System.out.println("totalCount" + totalCount);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cartCount",totalCount);

        String resultJson = new Gson().toJson(resultMap);
        resp.getWriter().write(resultJson);
    }
}
