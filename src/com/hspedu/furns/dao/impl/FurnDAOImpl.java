package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.utils.DataUtils;

import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class FurnDAOImpl extends BasicDAO<Furn> implements FurnDAO {

    //查询家居列表
    @Override
    public List<Furn> queryFurns() {
        String sql = "SELECT id, `name`,`maker`,price,sales,stock,img_path imgPath FROM furn";
        return queryMulti(sql,Furn.class);
    }

    //添加家居
    @Override
    public int addFurn(Furn furn) {
        String sql = "INSERT INTO furn(`name`,`maker`,price,sales,stock,img_path) \n" +
                "VALUES(?,?,?,?,?,?)";
        return update(sql,furn.getName(),furn.getMaker(),furn.getPrice(),
                furn.getSales(),furn.getStock(),furn.getImgPath());
    }

    @Override
    public int deleteFurnById(int furnId) {
        String sql = "DELETE FROM furn WHERE id = ?";
        return update(sql,furnId);
    }

    @Override
    public int updateFurn(Furn furn) {
        String sql = "UPDATE furn SET `name` = ?,`maker` = ?,price = ?,sales = ?,stock = ?,img_path = ?" +
                "\tWHERE id = ?";
        return update(sql,furn.getName(),furn.getMaker(),
                furn.getPrice(),furn.getSales(),furn.getStock(),furn.getImgPath(),furn.getId());
    }

    @Override
    public Furn queryFurnById(int id) {//img_path imgPath是为了正常反射，与javabean字段名保持一致
        String sql = "SELECT id, `name`,`maker`,price,sales,stock,img_path imgPath FROM furn WHERE id = ?";
        return querySingle(sql,Furn.class,id);
    }

    @Override
    public int getTotalRow() {
        String countSql = "SELECT COUNT(*) FROM furn";
        //数据库返回的对象类型不能直接去转换，因为双方规定的类型分类可能不一致
        //所有包装类都可以转成Number
        return ((Number)queryScalar(countSql)).intValue();
    }

    @Override
    public List<Furn> getPageItems(int begin,int pageSize) {
        int totalRow = getTotalRow();
        String sql = "SELECT id, `name`,`maker`,price,sales,stock,img_path imgPath " +
                "FROM furn LIMIT ?,?";

        return queryMulti(sql,Furn.class,begin,pageSize);
    }

    @Override
    public int getTotalRowByName(String name) {
        String sql = "SELECT COUNT(*) FROM furn WHERE `name` LIKE ?";
        return ((Number)queryScalar(sql,"%" + name + "%")).intValue();
    }

    @Override
    public List<Furn> getPageItemsByName(int begin, int pageSize, String name) {
        String sql = "SELECT id, `name`,`maker`,price,sales,stock,img_path imgPath " +
                "FROM furn WHERE `name` LIKE ? LIMIT ?,?";
        return queryMulti(sql,Furn.class,"%" + name + "%",begin,pageSize);
    }

}
