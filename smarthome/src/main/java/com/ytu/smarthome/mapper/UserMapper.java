package com.ytu.smarthome.mapper;

import com.ytu.smarthome.entity.User;

import java.util.List;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    Integer save(User user);

    /**
     * 根据用户uid删除用户数据
     *
     * @param uid 用户uid
     * @return 删除成功后返回的修改行数
     */
    Integer deleteByUid(Integer uid);

    /**
     * 更新用户基本资料
     *
     * @param user 用户资料
     * @return 受影响的行数
     */
    Integer updateInfo(User user);

    /**
     * 根据唯一用户名查找用户信息
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户id查询用户数据
     *
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    List<User> findUsers();

}
