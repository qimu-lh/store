package cn.d.service.impl;

import cn.d.dao.UserDao;
import cn.d.dao.impl.UserDaoImpl;
import cn.d.domain.Information;
import cn.d.domain.PageBean;
import cn.d.domain.User;
import cn.d.service.UserService;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        //调用Dao完成查询
        return dao.findAll();
    }

    @Override
    public User login(User user) {

        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public User findUserById(String id) {

        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] uids) {
        if (uids!=null && uids.length>0){
            for (String uid : uids) {
                dao.delete(Integer.parseInt(uid));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        PageBean<User> pageBean = new PageBean<User>();
        int currentPage = Integer.parseInt(_currentPage);//后面会修改现在不能赋值
        int rows = Integer.parseInt(_rows);
        pageBean.setRows(rows);


        pageBean.setRows(rows);
        if (currentPage <= 0){
            currentPage = 1;
        }

        int totalCount = dao.findTotalCount(condition);
        pageBean.setTotalCount(totalCount);

        int totalPage = (totalCount % rows) ==0 ? totalCount/rows : (totalCount/rows) + 1;

        pageBean.setTotalPage(totalPage);
        if (currentPage >= totalPage){
            currentPage = totalPage;
        }

        pageBean.setCurrentPage(currentPage);

        //计算开始的记录索引
        int start = (currentPage - 1) * rows;
        List<User> list = dao.findByPage(start,rows,condition);
        pageBean.setList(list);
        return pageBean;
    }

    @Override
    public boolean selectIdetityUsername(String username) {
        return dao.selsctIdentityUsernam(username);
    }

    @Override
    public User findDay10(String id) {
        return dao.findByOtherId(Integer.parseInt(id));
    }

    @Override
    public PageBean<Information> findInformationByPage(String currentPage, String rows, Map<String, String[]> condition) {
        return null;
    }

   /* @Override
    public PageBean<Information> findInformationByPage(String _currentPage1, String _now, Map<String, String[]> condition) {
        PageBean<Information> pageBean = new PageBean<Information>();
        int currentPage1 = Integer.parseInt(_currentPage1);//后面会修改现在不能赋值
        int now = Integer.parseInt(_now);
        pageBean.setRows(now);


        pageBean.setRows(now);
        if (currentPage1 <= 0){
            currentPage1 = 1;
        }

        int totalCount = dao.findTotalCount(condition);
        pageBean.setTotalCount(totalCount);

        int totalPage = (totalCount % now) ==0 ? totalCount/now : (totalCount/now) + 1;

        pageBean.setTotalPage(totalPage);
        if (currentPage1 >= totalPage){
            currentPage1 = totalPage;
        }

        pageBean.setCurrentPage(currentPage1);

        //计算开始的记录索引
        int start = (currentPage1 - 1) * now;
        List<Information> list1 = dao.findByPage(start,now,condition);
        pageBean.setList(list1);
        return pageBean;
    }*/


}
