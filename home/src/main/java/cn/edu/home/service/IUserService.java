package cn.edu.home.service;
import cn.edu.home.entity.User;

import java.util.List;

public interface IUserService {

    //用户注册功能
    void reg(User user);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户的信息
     */
    User login(String username, String password);

    /**
     * 根据用户id查询用户数据
     *
     * @param uid 用户的id
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User getByUid(Integer uid);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User getByUsername(String username);

    /**
     * 更新用户基本资料
     *
     * @param uid      用户id
     * @param username 用户名
     * @param user     封装了用户基本资料的对象
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 查询某用户的信息列表
     *
     * @param uid 用户id
     * @return 该用户信息列表
     */
    List<User> getUsers();

    /**
     * 删除用户
     *
     * @param uid
     */
    void delete(Integer uid);
}