package com.hspedu.furns.test;

import com.hspedu.furns.dao.OrderDAO;
import com.hspedu.furns.dao.impl.OrderDAOImpl;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class OrderTest {
    private OrderDAO orderDAO = new OrderDAOImpl();



    @Test
    public void saveOrder(){
        int saveOrder = orderDAO.saveOrder(new Order("sn001", new Date(), new BigDecimal(500), 0, 2));
        System.out.println(saveOrder);
    }



}
