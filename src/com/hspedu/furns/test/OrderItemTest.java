package com.hspedu.furns.test;

import com.hspedu.furns.dao.OrderItemDAO;
import com.hspedu.furns.dao.impl.OrderItemDAOImpl;
import com.hspedu.furns.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class OrderItemTest {
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Test
    public void saveOrderItem(){
        OrderItem orderItem = new OrderItem(null, "name", new BigDecimal(200),
                3, new BigDecimal(500), "sn001");
        orderItemDAO.saveOrderItem(orderItem);
        System.out.println(orderItem);
    }
}
