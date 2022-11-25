package com.hspedu.furns.test;

import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.CartItem;
import com.hspedu.furns.service.OrderService;
import com.hspedu.furns.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class OrderServiceTest {
    private OrderService orderService = new OrderServiceImpl();


    @Test
    public void saveOrder(){
        //构建一个cart对象
        Cart cart = new Cart();
        cart.addItem(new CartItem(13,"1111",new BigDecimal(111),2,new BigDecimal(222)));
        cart.addItem(new CartItem(29,"dddd",new BigDecimal(60),2,new BigDecimal(120)));

        System.out.println(orderService.saveOrder(cart,2));
    }

}
