package com.leyou.order.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author：lidatu
 * @Date： 2019/02/07 星期四 21:21
 * @Description：
 */

@Data
@Table(name = "tb_order_status")
public class OrderStatus {

    @Id
    private Long orderId;//订单id
    private Integer status;//订单状态
    private Date createTime;//创建时间
    private Date paymentTime;//付款时间
    private Date consignTime;//发货时间
    private Date endTime;//交易结束时间
    private Date closeTime;//交易结束时间
    private Date commentTime;//评价时间

}
