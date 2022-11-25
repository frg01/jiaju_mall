package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Member;

public interface MemberDAO {

    //提供一个用户名返回对应的member
    public Member queryMemberByUsername(String username);

    //添加会员，保存Member到数据库
    public int saveMember(Member member);

    //查询用户名是否存在以及密码是否正确
    public Member queryMemberByUsernameAndPwd(String username,String password);

    //验证管理员用户和密码是否存在
    public Member queryManagerByUsernameAndPwd(String username,String password);
}
