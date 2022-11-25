package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.entity.Member;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
public class MemberDAOImpl extends BasicDAO<Member> implements MemberDAO {

    /**
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 通过查询用户名和其对应的密码，存在返回member,不存在返回null
     */
    @Override
    public Member queryMemberByUsernameAndPwd(String username, String password) {
//        System.out.println("MemberDAOImpl的queryMemberByUsernameAndPwd方法。。。");
        String sql = "SELECT id,username,`password`,email FROM member \n" +
                "WHERE username = ? AND `password` = MD5(?)";
        return querySingle(sql, Member.class, username, password);

    }

    /**
     * @param username 通过用户名查询Member对象在数据库中是否存在
     * @return 不存在返回null
     */
    @Override
    public Member queryMemberByUsername(String username) {
        String sql = "SELECT id,username,`password`,email FROM member \n" +
                "WHERE username = ?";
        return querySingle(sql, Member.class, username);
    }

    /**
     *
     * @param member 将member对象存入数据库中
     * @return 成功返回被影响行数，失败返回-1
     */
    @Override
    public int saveMember(Member member) {
        String sql = "INSERT INTO member (username,`password`,email) \n" +
                "VALUES (?,MD5(?),?)";
        return update(sql, member.getUsername(),
                member.getPassword(), member.getEmail());
    }

    /**
     * 从admin表中验证管理员用户名和密码是否存在
     * @param username 登录名
     * @param password 登录密码
     * @return 数据库中存在就返回Member对象
     */
    public Member queryManagerByUsernameAndPwd(String username,String password){
        String sql = "SELECT username,`password` FROM admin \n" +
                "WHERE username = ? AND `password` = MD5(?)";
        return querySingle(sql,Member.class,username,password);
    }
}
