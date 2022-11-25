package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.Order;

import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public interface OrderDAO {

    /**
     * 存入订单表
     * @param order 对象
     * @return
     */
    public int saveOrder(Order order);

    /**
     * 通过该memberId查询订单
     * @param id
     * @return 返回order
     */
    public List<Order> queryOrderById(int id);

    /**
     * 拿到Order 通过 orderId
     * @param orderId
     * @return
     */
    public Order queryOrderByOrderId(String orderId);
}
