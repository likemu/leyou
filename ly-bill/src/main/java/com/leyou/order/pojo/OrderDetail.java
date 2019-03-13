package com.leyou.order.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author：lidatu
 * @Date： 2019/02/07 星期四 21:15
 * @Description：
 */

@Data
@Table(name = "tb_order_detail")
public class OrderDetail {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long orderId;//订单id
    private Long skuId;//商品id
    private Integer num;//商品购买数量
    private String title;//商品标题
    private Long price;//商品价格
    private String ownSpec;//商品规格参数
    private String image;//图片


}
