package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    List<User> findAll() throws Exception;

    //根据条件进行用户查询
    User findByCondition(User user) throws Exception;

    // 增加
    int addOneUser(User user) throws Exception;

    // 删除
    int deleteById(User user) throws Exception;

    // 修改
    int updateById(User user) throws Exception;


}
