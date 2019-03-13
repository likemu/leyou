package com.leyou.order.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Author：lidatu
 * @Date： 2019/02/07 星期四 19:50
 * @Description：
 */

@Data
@Table(name = "tb_order")
public class Order {

    @Id
    private Long orderId; //id
    private Long totalPay; //总金额
    private Long actualPay;//实时金额
    private Integer paymentType;//支付类型,1在线支付,2微货到付款

    private String promotionIds;//参与促销活动的id
    private Long postFee = 0L; //邮费
    private Date createTime; //创建时间
    private String shippingName;//物流名称
    private String shippingCode;//物流单号
    private Long userId;//用户id
    private String buyerMessage;//买家信息
    private String buyerNick;//卖家昵称
    private Boolean buyerRate;//买家是否已经评价
    private String receiver;//收货人全名
    private String receiverMobile;//移动电话
    private String receiverState;//省份
    private String receiverCity;//城市
    private String receiverDistrict;//区/县
    private String receiverAddress;//收货人地址, 如:xx路xx号
    private String receiverZip;//邮政编码, 如: 310001
    private Integer invoiceType = 0;//发票类型, 0无发票 1普通发票 2电子发票 3增值税发票
    private Integer sourceType = 1; //订单来源 1:app端 2:pc端 3:M端 4:微信端 5:手机qq端

    @Transient
    private OrderStatus orderStatus; //一对一的使用-订单状态

    @Transient
    private List<OrderDetail> orderDetails;//多对多的使用-订单详情
}










