package com.hspedu.furns.test;

import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.dao.impl.MemberDAOImpl;
import com.hspedu.furns.entity.Member;
import org.junit.jupiter.api.Test;

/**
 * @author: guorui fu
 * @versiion: 1.0
 * 测试MemberDAOImpl 的查询和存储对象的方法
 */
public class MemberDAOImplTest {
    private MemberDAO memberDAO = new MemberDAOImpl();
    @Test
    public void queryMemberByUsername(){
        System.out.println(memberDAO.queryMemberByUsername("jack123"));
    }

    @Test
    public void saveMember(){
        Member marry = new Member(null, "marry", "123456", "marry@qq.com");
        int i = memberDAO.saveMember(marry);
        System.out.println("返回被影响的行数" + i);
    }

    @Test
    public void queryMemberByUsernameAndPwd(){
        System.out.println("查询用户名和密码是否存在=" + memberDAO.queryMemberByUsernameAndPwd("admin","123456"));
    }

    @Test
    public void queryManagerByUsernameAndPwd(){
        Member member = memberDAO.queryManagerByUsernameAndPwd("admin", "adminsss");
        if (member != null){
            System.out.println("管理员账户存在。。");
        }else{
            System.out.println("管理员账户不存在。。");
        }
    }
}
