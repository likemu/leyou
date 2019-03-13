package com.leyou.cart.pojo;

import lombok.Data;

/**
 * @Author：lidatu
 * @Date： 2019/02/05 星期二 21:41
 * @Description：
 */

@Data
public class Cart {
    private Long userId;// 用户id
    private Long skuId;// 商品id
    private String title;// 标题
    private String image;// 图片
    private Long price;// 加入购物车时的价格
    private Integer num;// 购买数量
    private String ownSpec;// 商品规格参数
}
