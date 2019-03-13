package com.leyou.cart.service;

import com.leyou.auth.pojo.UserInfo;
import com.leyou.cart.interceptor.UserInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：lidatu
 * @Date： 2019/02/05 星期二 21:48
 * @Description：
 */

@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "cart:uid";

    public void addCart(Cart cart) {
        //获取当前登录用户
        UserInfo user = UserInterceptor.getUser();//同一个线程 能取出来 拦截器-controller-service(取线程)
        //key
        String key = KEY_PREFIX + user.getId();
        //hashKey
        String hashKey = cart.getSkuId().toString();
        //记录num  cart和cacheCart重复 可以不用记录 但为了优化/优雅 省了一个else判断
        Integer num = cart.getNum();
        BoundHashOperations<String, Object, Object> opration = redisTemplate.boundHashOps(key);
        //判断当前购物车商品,是否存在
        if (opration.hasKey(hashKey)) {
            //存在,修改数量
            //String json = opration.get(hashKey).toString();
            cart = JsonUtils.parse(opration.get(hashKey).toString(), Cart.class); //json转对象
            cart.setNum(cart.getNum() + num);
        }
        //写回redis
        opration.put(hashKey, JsonUtils.serialize(cart));//对象转json
    }

    public void addCartList(List<Cart> cart) {
        for (Cart newCart : cart) {
            addCart(newCart);
        }
    }

    public List<Cart> queryCartList() {
        //获取当前登录用户
        UserInfo user = UserInterceptor.getUser();//同一个线程 能取出来 拦截器-controller-service(取线程)
        //key
        String key = KEY_PREFIX + user.getId();
        if(!redisTemplate.hasKey(key)){
            //key不存在,返回404
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }
        //获取登录用户的所有购物车
        BoundHashOperations<String, Object, Object> opration = redisTemplate.boundHashOps(key);
        //List<Object> values = opration.values(); //返回的是List<Cart> 使用流
        List<Cart> carts = opration.values().stream()
                .map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
        return carts;
    }

    public void deleteCart(String skuId) {
        // 获取登录用户
        UserInfo user = UserInterceptor.getUser();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> operation = this.redisTemplate.boundHashOps(key);
        operation.delete(skuId);
    }

    public void updateNum(Long skuId, Integer num) {
        // 获取登录用户
        UserInfo user = UserInterceptor.getUser();
        String key = KEY_PREFIX + user.getId();
        String hashKey = skuId.toString();
        BoundHashOperations<String, Object, Object> opretion = this.redisTemplate.boundHashOps(key);
        if(!opretion.hasKey(hashKey)){
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }
        // 获取购物车
        Cart cart = JsonUtils.parse(opretion.get(hashKey).toString(), Cart.class);
        cart.setNum(num);
        // 写入购物车
        opretion.put(hashKey, JsonUtils.serialize(cart));
    }


    
}













