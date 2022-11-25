package com.hspedu.furns.service;

import com.hspedu.furns.entity.Member;

/**
 *@author: guorui fu
 *@versiion: 1.0
 */
public interface MemberService {

    //传入创建的member对象，通过用户名和密码进行登录
    public Member login(Member member);

    //注册用户
    public boolean registerMember(Member member);

    //判断用户是否存在
    public boolean isExistUsername (String username);

    //传入Member对象，通过用户名和密码进行管理员登录
    public Member managerLogin(Member member);
}
