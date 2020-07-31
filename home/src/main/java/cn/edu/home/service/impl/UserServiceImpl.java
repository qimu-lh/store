package cn.edu.home.service.impl;

import cn.edu.home.entity.User;
import cn.edu.home.mapper.UserMapper;
import cn.edu.home.service.IUserService;
import cn.edu.home.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    //添加持久层对象
    @Autowired
    private UserMapper userMapper;

    /**
     * 注册功能
     */
    @Override
    public void reg(User user) {
        // 从参数user对象中获取用户名
        String username = user.getUsername();
        // 调用userMapper根据用户名查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：用户名已存在，不允许注册，抛出UsernameConflictException异常
            throw new UsernameConflictException("用户名(" + username + ")已经被占用");
        }

        // 参数user是客户端提交的注册数据，并不包含隐藏数据
        // 执行加密
        String salt = UUID.randomUUID().toString().toUpperCase();
        //String md5Password = getMd5Password(user.getPassword(), salt);
        // 补全数据：salt
        user.setSalt(salt);
        // 补全数据：加密后的密码
        user.setPassword(user.getPassword());
        // 补全数据：is_delete，值为0
        user.setIsDelete(0);
        // 补全数据：4项日志，用户名为注册的用户名，时间为当前时间
        Date now = new Date();
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        // 调用userMapper的功能插入用户数据，实现注册，并获取插入数据时的返回值
        Integer rows = userMapper.save(user);
        // 判断以上返回值是否不为1
        if (rows != 1) {
            // 是：插入用户数据失败，抛出InsertException
            throw new InsertException("插入用户数据时出现未知错误");
        }
    }

    //登录功能
    @Override
    public User login(String username, String password) {
        // 根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：用户名不存在，抛出UserNotFoundException
            throw new UserNotFoundException("用户名不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：用户被标记为已删除，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据已被删除");
        }

        // 从查询结果中取出盐值
        String salt = result.getSalt();
        // 判断加密后的密码与查询结果中的密码是否不匹配
        if (!result.getPassword().equals(password)) {
            // 是：密码错误，抛出PasswordNotMatchException
            throw new PasswordNotMatchException("密码错误");
        }

        // 将查询结果中不应该返回的字段设置为null
        // 例如：isDelete、createdUser、createdTime、modifiedUser、modifiedTime、password、salt
        result.setIsDelete(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        result.setPassword(null);
        result.setSalt(null);
        // 返回查询结果
        return result;
    }

    //通过uid查询用户数据
    @Override
    public User getByUid(Integer uid) {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：用户数据不存在，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：用户被标记为已删除，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据已被删除");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的重要字段封装到新对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setPassword(result.getPassword());
        user.setTemperature(result.getTemperature());
        user.setHumidity(result.getHumidity());
        user.setAirMode(result.getAirMode());
        user.setAirTemperature(result.getAirTemperature());
        user.setAirWindSpeed(result.getAirWindSpeed());
        user.setLamp1(result.getLamp1());
        user.setLamp2(result.getLamp2());
        user.setLamp3(result.getLamp3());
        user.setLamp4(result.getLamp4());
        user.setWindowMode(result.getWindowMode());
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-HH:mm");
        Date date = result.getModifiedTime();
        //偷懒操作，按照自定义日期格式显示在界面，由于ModifiedTime事先设置好的为Data类型，这里
        //偷懒没有修改格式，而是利用了闲置的String ModifiedUser；字段
        user.setModifiedUser(formatter.format(date));
        // 返回新对象
        return user;
    }

    @Override
    public User getByUsername(String username) {
        // 根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：用户数据不存在，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：用户被标记为已删除，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据已被删除");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的重要字段封装到新对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setPassword(result.getPassword());
        user.setTemperature(result.getTemperature());
        user.setHumidity(result.getHumidity());
        user.setAirMode(result.getAirMode());
        user.setAirTemperature(result.getAirTemperature());
        user.setAirWindSpeed(result.getAirWindSpeed());
        user.setLamp1(result.getLamp1());
        user.setLamp2(result.getLamp2());
        user.setLamp3(result.getLamp3());
        user.setLamp4(result.getLamp4());
        user.setWindowMode(result.getWindowMode());
        // 返回新对象
        return user;
    }

    //更新用户数据
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：用户数据不存在，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            // 是：用户被标记为已删除，抛出UserNotFoundException
            throw new UserNotFoundException("用户数据已被删除");
        }

        // 将参数uid封装到参数user的uid中
        user.setUid(uid);
        // 将参数username封装到参数user的modifiedUser中
        user.setModifiedUser(username);
        // 创建时间对象，封装到参数user的modifiedTime中
        user.setModifiedTime(new Date());
        // 执行修改，并获取返回值
        Integer rows = userMapper.updateInfo(user);
        // 判断返回的受影响的行数是否不为1
        if (rows != 1) {
            // 是：更新错误，抛出UpdateException
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

    //查询用户信息列表
    @Override
    public List<User> getUsers() {
        return userMapper.findUsers();
    }

    @Override
    public void delete(Integer uid) {
        // 根据参数aid查询收货地址数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null：AddressNotFoundException
        if (result == null) {
            throw new UserNotFoundException(
                    "尝试访问的用户数据不存在，可能已经被删除");
        }

        // 判断查询结果中的uid与参数uid是否不匹配：AccessDeniedException
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        // 执行删除，并获取返回值
        Integer rows = userMapper.deleteByUid(uid);
        // 判断受影响行数是否不为1：DeleteException
        if (rows != 1) {
            throw new DeleteException("删除用户信息时出现未知错误");
        }

        // 判断返回值是否不为1：UpdateException
        if (rows != 1) {
            throw new UpdateException("更新数据时出现未知错误");
        }
    }

}

