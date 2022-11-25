package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.dao.impl.FurnDAOImpl;
import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;

import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class FurnServiceImpl implements FurnService {
    //定义属性FurnDAO 对象
    private FurnDAO furnDAO = new FurnDAOImpl();
    @Override
    public List<Furn> queryFurns() {
        return furnDAO.queryFurns();
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDAO.addFurn(furn);
    }

    @Override
    public int deleteFurnById(int furnId) {
        return furnDAO.deleteFurnById(furnId);
    }

    @Override
    public int updateFurn(Furn furn) {
        return furnDAO.updateFurn(furn);
    }

    @Override
    public Furn queryFurnById(int id) {
        return furnDAO.queryFurnById(id);
    }

    @Override
    public Page<Furn> page(int pageNum, int pageSize) {
        //先创建Page对象，根据情况填充属性
        Page<Furn> page = new Page<>();

        page.setPageNumber(pageNum);

        page.setPageSize(pageSize);

        int totalRow = furnDAO.getTotalRow();
        page.setTotalRow(totalRow);

        //计算有多少页 totalRow/pageSize
        int pageTotalCount = totalRow/pageSize;
        if (totalRow%pageSize > 0){
            pageTotalCount++;
        }
        page.setPageTotalCount(pageTotalCount);

        //mysql分页显示的计算公式，计算出begin
        int begin = pageSize*(pageNum - 1);
        page.setItems(furnDAO.getPageItems(begin,pageSize));

        return page;
    }

    @Override
    public Page<Furn> pageByName(int pageNum, int pageSize, String name) {
        //获取相关name的行数
        int totalRowByName = furnDAO.getTotalRowByName(name);
        //获取相关name，和指定页面返回Furn集合
        int begin = pageSize * (pageNum -1);
        List<Furn> items = furnDAO.getPageItemsByName(begin, pageSize, name);
        //集合返回了一共多少页
        int pageTotalCount = totalRowByName / pageSize;
        if (totalRowByName % pageSize > 0){
            pageTotalCount++;
        }
        return new Page<Furn>(pageNum,pageSize,pageTotalCount,totalRowByName,items,null);
    }


}
