package com.hspedu.furns.web;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.entity.OrderItem;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;
import com.hspedu.furns.utils.DataUtils;
import com.hspedu.furns.utils.JDBCUtilsByDruid;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

public class OrderServlet extends BasicServlet{
    private OrderService orderService = new OrderServiceImpl();

    //订单与订单详情存入，库存和销量变更
    protected void saveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("saveOrder");
        //从session中拿出Cart
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        System.out.println(cart);
        //如果cart为null,说明没有购买任何商品，转发到首页
        //购物车在session中，但是里面没有家居
        if (null == cart || cart.isEmpty()){
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return;
        }
        //member == null ,去登录
        Member member = (Member)session.getAttribute("member");
        System.out.println(member);
        if (null == member){
            request.getRequestDispatcher("/views/member/login.jsp").forward(request,response);
            return;
        }

        //生成订单 返回订单号
//        String orderId = null;
//        try {
//            orderId = orderService.saveOrder(cart, member.getId());
//            JDBCUtilsByDruid.commit();//提交
//        } catch (Exception e) {
//            JDBCUtilsByDruid.rollBack();
//            e.printStackTrace();
//        }
        String orderId = orderService.saveOrder(cart, member.getId());
//        System.out.println(orderId);
        request.getSession().setAttribute("orderId",orderId);
        //重新定向
        System.out.println("重新定向");
        response.sendRedirect(request.getContextPath() + "/views/order/checkout.jsp");
    }

    //通过订单号查询对应的订单详情
    protected void queryOrderItemById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从页面的orderItem拿到orderId属性
        String orderId = req.getParameter("orderId");
        List<OrderItem> orderItems = orderService.queryOrderItemById(orderId);
        //通过OrderId拿到对应的订单数据,前端页面进行了加减，这里注销
//        Order order = orderService.queryOrderByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }
        req.setAttribute("orderItems",orderItems);
//        req.setAttribute("order",order);
        req.getRequestDispatcher("views/order/order_detail.jsp").forward(req,resp);
    }

    //通过memberId查询对应的order表
    protected void queryOrderById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member member = (Member)req.getSession().getAttribute("member");
        //从session中拿到用户id
        Integer id = member.getId();
        if (null != id){
            List<Order> orders = orderService.queryOrderById(id);
            req.setAttribute("orders",orders);
            req.getRequestDispatcher("/views/order/order.jsp").forward(req,resp);
        }else{
            resp.sendRedirect(req.getContextPath() + "/views/member/login.jsp");
        }
    }
}
