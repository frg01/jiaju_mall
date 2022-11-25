package com.hspedu.furns.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class Order {

//    id VARCHAR(64) PRIMARY KEY NOT NULL ,
// `create_time` DATETIME NOT NULL,  -- 订单生成时间
// `price` DECIMAL(11,2) NOT NULL,
// `status` TINYINT NOT NULL, -- 0未发货，1已结账
//    member_id INT NOT NULL
    private String id;//订单号
    private Date createTime;
    private BigDecimal price;
    private Integer status;
    private Integer memberId;

    public Order() {
    }

    public Order(String id, Date createTime, BigDecimal price, Integer status, Integer memberId) {
        this.id = id;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", memberId=" + memberId +
                '}';
    }
}
