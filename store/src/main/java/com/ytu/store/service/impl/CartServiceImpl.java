package com.ytu.store.service.impl;

import com.ytu.store.entity.Cart;
import com.ytu.store.mapper.CartMapper;
import com.ytu.store.service.ICartService;
import com.ytu.store.service.IProductService;
import com.ytu.store.service.ex.*;
import com.ytu.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private IProductService productService;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer num, String username) {
        // 根据参数uid和pid查询数据
        Cart result = findByUidAndPid(uid, pid);
        Date now = new Date();
        // 判断查询结果是否为null
        if (result == null) {
            // 通过productService查出商品价格
            Long price = productService.findByPid(pid).getPrice();
            // 执行添加
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(num);
            cart.setPrice(price);
            cart.setCreatedUser(username);
            cart.setModifiedUser(username);
            cart.setCreatedTime(now);
            cart.setModifiedTime(now);
            save(cart);
        } else {
            // 从查询结果中获取原数量，和参数num相加，得到新数量
            Integer number = result.getNum() + num;
            // 执行更新数量
            updateNum(result.getCid(), number, username, now);
        }
    }

    @Override
    public void deleteByUid(Integer uid) {
        List<CartVO> result = findByUid(uid);
        if (result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }
        //此处若成功会删除所有购物车记录，返回结果为int值，值根据用户购物车条数而定，不一定为1
        cartMapper.deleteByUid(uid);
    }

    @Override
    public void deleteByCid(Integer cid) {
        Cart result=cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }
        Integer rows = cartMapper.deleteByCid(cid);
        if (rows!=1){
            throw new DeleteException("删除购物车时出现异常！请稍后再试！");
        }
    }

    @Override
    public List<CartVO> getByUid(Integer uid) {
        return findByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        // 根据参数cid查询购物车数据
        Cart result = findByCid(cid);
        // 判断查询结果是否为null：CartNotFoundException
        if (result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致：AccessDeniedException
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        // 从查询结果中取出num，并加1
        Integer number = result.getNum() + 1;
        // 执行更新数量
        updateNum(cid, number, username, new Date());
        // 返回新的数量
        return number;
    }

    @Override
    public Integer subNum(Integer cid, Integer uid, String username) {
        // 根据参数cid查询购物车数据
        Cart result = findByCid(cid);
        // 判断查询结果是否为null：CartNotFoundException
        if (result == null) {
            throw new CartNotFoundException("购物车数据不存在");
        }

        // 判断查询结果中的uid与参数uid是否不一致：AccessDeniedException
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        // 从查询结果中取出num，并减1
        Integer number = result.getNum() - 1;
        if (number>1){
            // 执行更新数量
            updateNum(cid, number, username, new Date());
        }
        // 返回新的数量
        return number;
    }

    /**
     * 向购物车数据表中插入数据
     * @param cart 购物车数据
     */
    private void save(Cart cart) {
        Integer rows = cartMapper.save(cart);
        if (rows != 1) {
            throw new InsertException("插入购物车数据时出现未知错误");
        }
    }

    /**
     * 修改购物车表中商品的数量
     * @param cid 购物车数据的id
     * @param num 新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     */
    private void updateNum(Integer cid, Integer num,
                           String modifiedUser, Date modifiedTime) {
        Integer rows = cartMapper.updateNum(cid, num, modifiedUser, modifiedTime);
        if (rows != 1) {
            throw new UpdateException("更新购物车数据时出现未知错误");
        }
    }

    /**
     * 获取某用户在购物车中添加的某商品的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 相关的购物车数据，如果没有匹配的数据，则返回null
     */
    private Cart findByUidAndPid(Integer uid, Integer pid) {
        return cartMapper.findByUidAndPid(uid, pid);
    }

    /**
     * 查询某用户的购物车数据列表
     * @param uid 用户的id
     * @return 该用户的购物车数据列表
     */
    private List<CartVO> findByUid(Integer uid) {
        return cartMapper.findByUid(uid);
    }

    /**
     * 根据cid查询购物车信息
     * @param cid
     * @return
     */
    private Cart findByCid(Integer cid){
        return cartMapper.findByCid(cid);
    }

}
