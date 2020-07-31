package cn.d.dao.impl;

import cn.d.dao.UserDao;
import cn.d.domain.User;
import cn.d.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库...
        //1.定义sql
        String sql = "select * from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    @Override
    public void add(User user) {

        String sql = "insert into user values(null,?,?,?,?,?,?,null,null,0)";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());

    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql,id);
    }

    @Override
    public void update(User user) {
        String sql = "update user set name =?,gender =?, age = ?," +
                "address = ?, qq=?, email=?, identity=0 where id = ?";
        template.update(sql,user.getName(),user.getGender(),
                user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义一个模板sql
        String sql = "select count(*) from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> set = condition.keySet();
        List<Object> params = new ArrayList<Object>();
        for (String key : set) {
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
//            System.out.println("value"+value);
            //判断使用有值
            if (value != null && !"".equals(value)){
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//添加条件的值
            }
        }
//        System.out.println("sb.toString"+sb.toString());
//        System.out.println(params);
        return template.queryForObject(sb.toString(), Integer.class,params.toArray());//可变参，转换为数组
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> set = condition.keySet();
        List<Object> params = new ArrayList<Object>();
        for (String key : set) {
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
//            System.out.println("value"+value);
            //判断使用有值
            if (value != null && !"".equals(value)){
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//添加条件的值
            }
        }
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);

        sql = sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }

    @Override
    public boolean selsctIdentityUsernam(String username) {
        User user=null;
        try {
            String sql = "select IDENTITY from user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!user.getIdentity().equals("1")){
            return false;
        }

        return true;
    }

    @Override
    public User findByOtherId(int id) {
        String sql = "select * from infermation where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

}
