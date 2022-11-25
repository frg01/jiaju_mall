package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.CartItem;
import com.hspedu.furns.entity.OrderItem;

import java.util.*;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class OrderItemDAOImpl extends BasicDAO<OrderItem> implements OrderItemDAO {

    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (id,`name`,price,`count`,total_price,order_id) \n" +
                "  VALUES(?,?,?,?,?,?)";
//        //遍历购物车，放入OrderItem
//        HashMap<Integer, CartItem> items = cart.getItems();
//        int updateRow = 0;
//        Set<Integer> keys = items.keySet();
//        for (Integer key : keys) {
//            CartItem cartItem = items.get(key);
//            updateRow = update(sql, null, cartItem.getName(), cartItem.getPrice(), cartItem.getCount(),
//                    cartItem.getTotalPrice(), orderId);
//            updateRow += updateRow;
//        }
//
//        return updateRow;
        return update(sql,orderItem.getId(),orderItem.getName(),orderItem.getPrice(),
                orderItem.getCount(),orderItem.getTotalPrice(),orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItemById(String id) {
        String sql = "SELECT id ,`name`,price,`count`,total_price totalPrice,order_id orderId\n" +
                "\tFROM order_item WHERE order_id = ?";
        return queryMulti(sql, OrderItem.class, id);
    }

}
