package com.hspedu.furns.test;

import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.impl.MemberServiceImp;
import com.sun.xml.internal.fastinfoset.util.ValueArrayResourceException;
import org.junit.jupiter.api.Test;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class MemberServiceImpTest {
    private MemberServiceImp memberServiceImp = new MemberServiceImp();

    @Test
    public void login(){

        Member member = memberServiceImp.login(new Member(null, "admin", "admin", null));
        if (null != member){
            System.out.println("用户可以登录。。");
        }else{
            System.out.println("用户不可以登录。。");
        }
    }
    @Test
    public void isExistUsername() {
        if (memberServiceImp.isExistUsername("admin")){
            System.out.println("用户名存在");
        }else{
            System.out.println("用户名不存在");
        }
    }
    @Test
    public void registerMember() {
        Member member = new Member(null, "alan", "alan", "555sa@qq.com");
        if (memberServiceImp.registerMember(member)){
            System.out.println("用户存入成功");
        }else{
            System.out.println("用户存入失败");
        }
    }
    @Test
    public void managerLogin(){
        Member member = new Member(null, "admin", "admin", null);
        Member member1 = memberServiceImp.managerLogin(member);

        if (member1 != null){
            System.out.println("管理员账号存在。。");
        }else{
            System.out.println("管理员账号不存在。。");
        }
    }
}
