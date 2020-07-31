package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {

    /**
     * 为Dao接口生成代理实现类
     */
    <T> T getMapper(Class<?> mapperClass);

    /**
     * 查询所有
     */
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    /**
     * 根据条件查询单个
     */
    <T> T selectOne(String statementId, Object... params) throws Exception;


    /**
     * 插入
     */
    int insert(String statementId, Object... params) throws Exception;


    /**
     * 删除
     */
    int delete(String statementId, Object... params) throws Exception;


    /**
     * 更改
     */
    int update(String statementId, Object... params) throws Exception;


}
