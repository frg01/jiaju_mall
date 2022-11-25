package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.dao.impl.MemberDAOImpl;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class MemberServiceImp implements MemberService {
    //定义一个MemberDAO属性
    private MemberDAO memberDAO = new MemberDAOImpl();

    //判断登录是否成功

    /**
     *
     * @param member 对象
     * @return 数据库验证通过返回true 否则返回false
     */
    @Override
    public Member login(Member member) {
        return memberDAO.queryMemberByUsernameAndPwd
                (member.getUsername(),member.getPassword());
    }

    @Override
    public boolean registerMember(Member member) {
        return memberDAO.saveMember(member) == 1 ? true : false;
    }

    /**
     * 判断用户名是否存在
     * @param username  用户名
     * @return 存在返回true，否则返回false
     */
    @Override
    public boolean isExistUsername(String username) {
       //三元运算符 如果等于null 返回true 否则false
        return memberDAO.queryMemberByUsername(username) == null ? false : true;
    }

    /**
     * 判断管理员名和密码是否存在
     * @param member 携带登录名和密码
     * @return 返回Member对象
     */
    @Override
    public Member managerLogin(Member member) {
       return memberDAO.queryManagerByUsernameAndPwd(member.getUsername(),member.getPassword());
    }
}
