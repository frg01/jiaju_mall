package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.OrderDAO;
import com.hspedu.furns.entity.Cart;
import com.hspedu.furns.entity.CartItem;
import com.hspedu.furns.entity.Order;
import com.hspedu.furns.utils.JDBCUtilsByDruid;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class OrderDAOImpl extends BasicDAO<Order> implements OrderDAO {
    @Override
    public int saveOrder(Order order) {
        //添加订单
        String sql = "INSERT INTO `order` (id,`create_time`,price,`status`,member_id)\n" +
                "\tVALUES(?,?,?,?,?)";

        return update(sql,order.getId(),order.getCreateTime(),order.getPrice(),
                order.getStatus(),order.getMemberId());
//        //修改商品库存与销售量
//        String sql2 = "UPDATE  furn SET sales = sales + ? ,stock = stock - ? WHERE id =?";
//        int saveOrder = 0;
//        try {
//            //先进行订单存入成功后，在进行商品库存与销售量的修改
//            Connection connection = JDBCUtilsByDruid.getConnection();
//            connection.setAutoCommit(false);
//            saveOrder = update(sql, order.getId(), order.getCreateTime(),
//                    order.getPrice(), order.getStatus(), order.getMemberId());
//            //商品库存，销量修改
//            Set<Integer> keys = cart.getItems().keySet();
//            for (Integer key : keys) {
//                //拿到商品id与购买量
//                CartItem cartItem = cart.getItems().get(key);
//                Integer id = cartItem.getId();
//                Integer count = cartItem.getCount();
//                update(sql2,count,count,id);
//            }
//            //提交事务
//            connection.commit();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return saveOrder;
    }

    @Override
    public List<Order> queryOrderById(int id) {
        String sql = "SELECT id,`create_time` createTime,`price`,`status`,member_id memberId \n" +
                "\tFROM `order` WHERE member_id = ? ORDER BY `create_time` DESC";
        return queryMulti(sql,Order.class,id);
    }

    @Override
    public Order queryOrderByOrderId(String orderId) {
        String sql = "SELECT id,`create_time` createTime,`price`,`status`,member_id memberId \n" +
                "\tFROM `order` WHERE id = ?";
        return querySingle(sql,Order.class,orderId);
    }
}
