package com.hspedu.furns.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

/**Cart 购物车，包含多个CartItem
 * @author: guorui fu
 * @versiion: 1.0
 */
public class Cart {

    //包含多个包含多个CartItem，使用HashMap来储存
    private HashMap<Integer, CartItem> items = new HashMap<>();

    public boolean isEmpty(){
        return items.size() == 0;
    }
    /**
     * 修改商品数量 根据传入的id 和 count
     * @param id
     * @param count
     */
    public void updateCount(int id,int count){
        //拿到对应cartItem 修改次数
        CartItem cartItem = items.get(id);
        //如果得到CartItem 先更新数量，在更新总价
        if (null != items){
            cartItem.setCount(count);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }

    }
    //购物车商品总数量
    public int getTotalCount(){
        int totalCount = 0;
        //遍历items 统计totalCount
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            totalCount += items.get(id).getCount();
        }
        return totalCount;
    }

    //添加家居到Cart
    public void addItem(CartItem cartItem){
        CartItem item = items.get(cartItem.getId());
        if (item == null){//说明当前购物车，还没有这个cartItem
            items.put(cartItem.getId(),cartItem);
        }else{//购物车中有这个cartItem
            item.setCount(item.getCount() + 1);//数量+1
            //修改总价
            //bigDecimal运算有相应的方法，不能直接符号运算
            item.setTotalPrice(item.getTotalPrice().add(item.getPrice()));
        }
    }

    public BigDecimal cartTotalPrice(){
        BigDecimal totalPrice = new BigDecimal(0);
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            CartItem cartItem = items.get(id);
            //add计算之后，重新赋给totalPrice 这样才能累加
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
        return totalPrice;
    }

    //删除购物车中某商品
    public void deleteItem(int id){
        items.remove(id);
    }

    //清空购物车
    public void clear(){
        items.clear();
    }
    public HashMap<Integer, CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
