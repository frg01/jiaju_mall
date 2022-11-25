package com.hspedu.furns.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class DataUtils {

    //封装表单数据到Javabean中，表单中的name与bean中的字段名要一致
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    //将字符串转成数字，否则返回默认值
    public static int parseInt(String val,int defaultVal ){
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            System.out.println(val + "格式不正确。。。");
        }
        return defaultVal;
    }
}
