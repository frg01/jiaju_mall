package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.dao.OrderDAO;
import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.dao.impl.FurnDAOImpl;
import com.hspedu.furns.dao.impl.OrderDAOImpl;
import com.hspedu.furns.dao.impl.OrderItemDAOImpl;
import com.hspedu.furns.entity.*;
import com.hspedu.furns.service.OrderService;

import java.util.*;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public String saveOrder(Cart cart, int memberId) {
        //业务相对综合 多表事务= TreadLocal+Mysql事务机制+过滤器 ，考虑比较多
        //先通过该cart构建对应的Order对象
        //先生成一个UUID，表示当前的订单号  要保证是唯一的
        String orderId = System.currentTimeMillis() + "" + memberId;
        Order order =
                new Order(orderId, new Date(), cart.cartTotalPrice(), 0, memberId);
        //保存order到数据表
        orderDAO.saveOrder(order);

        //通过cart对象，遍历出CartItem，构建OrderItem对象，并保存到Order_item表
//        HashMap<Integer, CartItem> items = cart.getItems();
//        Set<Integer> keys = items.keySet();
//        for (Integer key : keys) {
//            CartItem cartItem = items.get(key);
//            //通过cartItem对象构建了OrderIem
//            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getPrice(),
//                    cartItem.getCount(), cartItem.getTotalPrice(), orderId);
//            orderItemDAO.saveOrderItem(orderItem);
//
//            //更新furn表的sales,stock
//            //拿到furn对象
//            Furn furn = furnDAO.queryFurnById(cartItem.getId());
//            furn.setSales(furn.getSales() + cartItem.getCount());
//            furn.setStock(furn.getStock() - cartItem.getCount());
//            //更新到数据库
//            furnDAO.updateFurn(furn);
//        }
        //第二种entrySet遍历方式
        for (Map.Entry<Integer,CartItem> entry: cart.getItems().entrySet()) {
            CartItem cartItem = entry.getValue();
            //通过cartItem对象构建了OrderIem
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getPrice(),
                    cartItem.getCount(), cartItem.getTotalPrice(), orderId);
            orderItemDAO.saveOrderItem(orderItem);

            //更新furn表的sales,stock
            //拿到furn对象
            Furn furn = furnDAO.queryFurnById(cartItem.getId());
            furn.setSales(furn.getSales() + cartItem.getCount());
            furn.setStock(furn.getStock() - cartItem.getCount());
            //更新到数据库
            furnDAO.updateFurn(furn);
        }
        //清空购物车
        cart.clear();
        return orderId;
    }

    @Override
    public List<OrderItem> queryOrderItemById(String id) {
        return orderItemDAO.queryOrderItemById(id);
    }

    @Override
    public List<Order> queryOrderById(int id) {
        return orderDAO.queryOrderById(id);
    }

    @Override
    public Order queryOrderByOrderId(String orderId) {
        return orderDAO.queryOrderByOrderId(orderId);
    }
}
