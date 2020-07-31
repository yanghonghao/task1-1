package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;

public class T001 {

    private SqlSession sqlSession;

    @Before
    public void before() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        sqlSession = sqlSessionFactory.openSession();

    }

    @After
    public void after() throws Exception {
        // sqlSession.close();
    }

    /**
     * sqlSession 直接执行 sql 语句的方式
     */
    @Test
    public void test1() throws Exception {
        User user = new User();
        user.setId(4);
        user.setUsername("lagou");

        // 清理，确保没有 用户4
        sqlSession.delete("com.lagou.dao.IUserDao.deleteById", user);

        // 查看插入用户前的用户列表
        List<User> userList = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        System.out.println("插入用户前的用户列表");
        System.out.println(userList);

        System.out.println("将要插入的用户为");
        System.out.println(user);

        // 插入用户，并查看用户列表
        sqlSession.insert("com.lagou.dao.IUserDao.addOneUser", user);
        userList = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        System.out.println("插入用户后的用户列表");
        System.out.println(userList);

        // 更改用户，并查看用户列表
        user.setUsername("lagou 666");
        sqlSession.update("com.lagou.dao.IUserDao.updateById", user);
        userList = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        System.out.println("更改用户名后的用户列表");
        System.out.println(userList);


        // 删除用户，并查看用户列表
        sqlSession.delete("com.lagou.dao.IUserDao.deleteById", user);
        userList = sqlSession.selectList("com.lagou.dao.IUserDao.findAll");
        System.out.println("删除用户后的用户列表");
        System.out.println(userList);
    }

    /**
     * mapper 代理执行 sql
     */
    @Test
    public void test2() throws Exception {
        User user = new User();
        user.setId(4);
        user.setUsername("lagou");

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 清理，确保没有 用户4
        userDao.deleteById(user);


        // 查看插入用户前的用户列表
        List<User> userList = userDao.findAll();
        System.out.println("插入用户前的用户列表");
        System.out.println(userList);

        System.out.println("将要插入的用户为");
        System.out.println(user);

        // 插入用户，并查看用户列表
        userDao.addOneUser(user);
        userList = userDao.findAll();
        System.out.println("插入用户后的用户列表");
        System.out.println(userList);

        // 更改用户，并查看用户列表
        user.setUsername("lagou 666");
        userDao.updateById(user);
        userList = userDao.findAll();
        System.out.println("更改用户名后的用户列表");
        System.out.println(userList);


        // 删除用户，并查看用户列表
        userDao.deleteById(user);
        userList = userDao.findAll();
        System.out.println("删除用户后的用户列表");
        System.out.println(userList);

    }

}
