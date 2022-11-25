package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;

import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public interface FurnDAO {

    /**
     * 返回所有家居信息集合，后面在考虑分页
     * @return
     */
    public List<Furn> queryFurns();

    /**
     * 添加家居
     * @return  返回被影响行数
     */
    public int addFurn(Furn furn);

    /**
     * 删除家居
     * @param furnId
     * @return 返回被影响个数
     */
    public int deleteFurnById(int furnId);

    /**
     * 修改传入家居的字段内容
     * @param furn
     * @return 返回被影响行数
     */
    public int updateFurn(Furn furn);

    /**
     * 利用id查询数据库中的Furn
     * @param id
     * @return 返回Furn对象
     */
    public Furn queryFurnById(int id);


    //得到表中数据行数
    public int getTotalRow();

    /**
     * 获取当前页要显示的数据
     * @param pageNum
     * @param begin
     * @return
     */
    public List<Furn> getPageItems(int begin,int pageSize);

    //通过搜索的名字得到符合条件的行数
    public int getTotalRowByName(String name);

    /**
     * 通过begin,pageSize,name（家居名）,得到相对应的List集合
     * @param begin
     * @param pageSize
     * @param name
     * @return
     */
    public List<Furn> getPageItemsByName(int begin,int pageSize,String name);

}
