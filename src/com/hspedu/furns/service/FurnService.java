package com.hspedu.furns.service;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;

import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public interface FurnService {

    /**
     * 返回家居信息
     * @return
     */
    public List<Furn> queryFurns();

    /**
     * 添加家居
     * @param furn
     * @return 返回添加个数（数据库被影响行数）
     */
    public int addFurn(Furn furn);

    /**
     * 删除家居
     * @param furnId
     * @return 返回被影响行数
     */
    public int deleteFurnById(int furnId);

    /**
     * 修改家居信息
     * @param furn
     * @return
     */
    public int updateFurn(Furn furn);

    /**
     * 根据id返回Furn对象
     * @param id
     * @return
     */
    public Furn queryFurnById(int id);

    /**
     * 根据参数返回相应页数的page信息
     * @param begin 开始
     * @param pageNum 每一页展示多少
     * @return
     */
    public Page<Furn> page(int pageNum,int pageSize);

    /**
     * 根据搜索的名字返回相关page页面
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    public Page<Furn> pageByName(int pageNum,int pageSize,String name);
}
