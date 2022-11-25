package com.hspedu.furns.entity;

import java.math.BigDecimal;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class OrderItem {

//    id INT PRIMARY KEY  AUTO_INCREMENT,-- 订单项的id
//  `name` VARCHAR(64) NOT NULL,
//    price DECIMAL(11,2) NOT NULL,
//  `count` INT NOT NULL,
//    total_price DECIMAL(12.2) NOT NULL,
//    order_id VARCHAR(64) NOT NULL
    private Integer id ;
    private String name;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;
    private String orderId;

    public OrderItem() {
    }

    public OrderItem(Integer id, String name, BigDecimal price, Integer count, BigDecimal totalPrice, String orderId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", totalPrice=" + totalPrice +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
