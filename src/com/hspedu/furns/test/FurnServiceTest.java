package com.hspedu.furns.test;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class FurnServiceTest {
    private FurnService furnService = new FurnServiceImpl();
    @Test
    public void FurnService(){
        List<Furn> furns = furnService.queryFurns();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }

    @Test
    public void addFurn(){
        Furn furn = new Furn(null, "特斯家享", "特斯", new BigDecimal(2001.33),
                22, 200, "assets/images/product-image/4.jpg");
        int i = furnService.addFurn(furn);
        if (i >= 1){
            System.out.println("添加家居成功。。。");
        }else{
            System.out.println("添加家居失败");
        }
    }
}
