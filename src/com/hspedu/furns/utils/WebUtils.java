package com.hspedu.furns.utils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 判断是不是Ajax请求
 *
 * @author: guorui fu
 * @versiion: 1.0
 */
public class WebUtils {
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    //定义一个文件上传的路径常量
    public static String FURN_IMG_DIRECTORY = "assets/images/product-image";
    //日期目录的字符串
    public static String getYearMonthDay() {
        //如何得到当前的日期-> java基础 日期 三代类
        LocalDateTime ldt = LocalDateTime.now();
        int year = ldt.getYear();
        int monthValue = ldt.getMonthValue();
        int dayOfMonth = ldt.getDayOfMonth();
        String yearMonthDay = year + "/" + monthValue + "/" + dayOfMonth + "/";
        return yearMonthDay;
    }
}
