package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.OrderItem;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

/**
 * 订单项
 * @author: guorui fu
 * @versiion: 1.0
 */
public interface OrderItemDAO {

    /**
     *
     * @param orderItem
     * @return
     */
    public int saveOrderItem(OrderItem orderItem);

    /**
     * 查订单详情
     * @param id 订单号
     */
    public List<OrderItem> queryOrderItemById(String id);
}
