package com.hspedu.furns.service;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.entity.OrderItem;

import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public interface OrderService {

    /**
     * 存入订单
     * @param cart   通过web层传入saveOrder
     * @param memberId
     * @return 返回被影响行数
     */
    public String saveOrder(Cart cart,int memberId);

    /**
     * 查询订单详情
     * @param id 订单号
     * @return 返回id对应的订单详情
     */
    public List<OrderItem> queryOrderItemById(String id);

    /**
     * 通过memberId 查所有订单
     * @param id
     * @return
     */
    public List<Order> queryOrderById(int id);

    public Order queryOrderByOrderId(String orderId);
}
