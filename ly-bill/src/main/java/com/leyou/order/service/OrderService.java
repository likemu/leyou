package com.leyou.order.service;

import com.leyou.auth.pojo.UserInfo;
import com.leyou.common.dto.CartDTO;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.IdWorker;
import com.leyou.item.pojo.Sku;
import com.leyou.order.client.AddressClient;
import com.leyou.order.client.GoodsClient;
import com.leyou.order.dto.AddressDTO;
import com.leyou.order.dto.OrderDTO;
import com.leyou.order.enums.OrderStatusEnum;
import com.leyou.order.enums.PayState;
import com.leyou.order.interceptors.UserInterceptor;
import com.leyou.order.mapper.OrderDetailMapper;
import com.leyou.order.mapper.OrderMapper;
import com.leyou.order.mapper.OrderStatusMapper;
import com.leyou.order.pojo.Order;
import com.leyou.order.pojo.OrderDetail;
import com.leyou.order.pojo.OrderStatus;
import com.leyou.order.utils.PayHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.rmi.runtime.Log;

import java.rmi.dgc.Lease;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author：lidatu
 * @Date： 2019/02/08 星期五 0:06
 * @Description： 创建订单的接口
 */

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper detailMapper;
    @Autowired
    private OrderStatusMapper statusMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private PayHelper payHelper; //下单成功后调用支付助手

    @Transactional
    public Long createOrder(OrderDTO orderDTO) {
        //1新增订单
        Order order = new Order();

        //1.1订单编号,基本信息
        long orderId = idWorker.nextId();
        order.setOrderId(orderId);
        order.setCreateTime(new Date());
        order.setPaymentType(orderDTO.getPaymentType());

        //1.2用户信息
        UserInfo user = UserInterceptor.getUser();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        order.setBuyerRate(false);//是否评价

        //1.3收货人地址
        //获取收货人信息
        AddressDTO addr = AddressClient.findById(orderDTO.getAddressId());
        order.setReceiver(addr.getName());//收货人
        order.setReceiverState(addr.getState());//省
        order.setReceiverCity(addr.getCity());//市
        order.setReceiverDistrict(addr.getDistrict());//区/县
        order.setReceiverAddress(addr.getAddress());//详细地址
        order.setReceiverMobile(addr.getPhone());//手机
        order.setReceiverZip(addr.getZipCode());//邮编

        //1.4金额
//        List<CartDTO> cartDTOS = orderDTO.getCarts();
//        List<Long> ids = cartDTOS.stream().map(CartDTO::getSkuId).collect(Collectors.toList());
        //把CartDTO 转为一个map key是sku的id,值是num
        Map<Long, Integer> numMap = orderDTO.getCarts().stream()
                .collect(Collectors.toMap(CartDTO::getSkuId, CartDTO::getNum));
        //获取所有sku的id
        Set<Long> ids = numMap.keySet();
        //根据id查询sku
        List<Sku> skus = goodsClient.querySkuByIds(new ArrayList<>(ids));//Set集合变成List集合

        //准备orderDetail集合
        List<OrderDetail> details = new ArrayList<>();

        long totalPay = 0L;
        for (Sku sku : skus) {
            //计算商品总价
            totalPay += sku.getPrice() * numMap.get(sku.getId());//价格*数量 Map集合 通过键id获取值num

            //封装orderDetail
            OrderDetail detail = new OrderDetail();
            detail.setImage(StringUtils.substringBefore(sku.getImages(), ","));
            detail.setNum(numMap.get(sku.getId()));
            detail.setOrderId(orderId);
            detail.setOwnSpec(sku.getOwnSpec());
            detail.setPrice(sku.getPrice());
            detail.setSkuId(sku.getId());
            detail.setTitle(sku.getTitle());

            details.add(detail);
        }
        order.setTotalPay(totalPay);
        //实付金额 = 总金额 + 邮费 - 优惠
        order.setActualPay(totalPay + order.getPostFee() - 0);
        //1.5 order写入数据库
        int count = orderMapper.insertSelective(order);
        if(count != 1){
            log.error("[订单服务] 创建订单失败, orderId:{}", orderId);
            throw new LyException(ExceptionEnum.CREATE_ORDER_ERROR);
        }


        //2新增订单详情
        count = detailMapper.insertList(details);
        if(count != details.size()){
            log.error("[订单服务] 创建订单失败, orderId:{}", orderId);
            throw new LyException(ExceptionEnum.CREATE_ORDER_ERROR);
        }

        //3新增订单状态
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCreateTime(order.getCreateTime());
        orderStatus.setOrderId(orderId);
        orderStatus.setStatus(OrderStatusEnum.UN_PAY.value());
        count = statusMapper.insertSelective(orderStatus);
        if(count != 1){
            log.error("[订单服务] 创建订单失败, orderId:{}", orderId);
            throw new LyException(ExceptionEnum.CREATE_ORDER_ERROR);
        }

        //4 减库存
        List<CartDTO> cartDTOS = orderDTO.getCarts();
        goodsClient.decreaseStock(cartDTOS);

        return orderId;
    }

    public Order queryOrderById(Long id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if(order == null){
            //不存在
            throw new LyException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        //查询订单详情
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(id);
        List<OrderDetail> details = detailMapper.select(detail);//在详情表中orderId不是主键 需要这样查询
        if(CollectionUtils.isEmpty(details)){
            throw new LyException(ExceptionEnum.ORDER_DETAIL_NOT_FOUND);
        }
        order.setOrderDetails(details);//设入订单详情
        //查询订单状态
        OrderStatus orderStatus = statusMapper.selectByPrimaryKey(id);
        if(orderStatus == null){
            //不存在
            throw new LyException(ExceptionEnum.ORDER_STATUS_NOT_FOUND);
        }
        order.setOrderStatus(orderStatus);//设入订单状态
        return order;
    }

    /**
     * 创建支付链接
     * @param orderId
     * @return
     */
    public String createPayUrl(Long orderId) {
        //查询订单
        Order order = queryOrderById(orderId);
        //判断订单状态
        Integer status = order.getOrderStatus().getStatus();
        if(status != OrderStatusEnum.UN_PAY.value()){ //不是未付款的订单 即已支付了
            //订单状态异常
            throw new LyException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //支付金额
        Long actualPay = order.getActualPay();
        //商品描述
        OrderDetail desc = order.getOrderDetails().get(0);
        return payHelper.createOrder(orderId, actualPay, desc.getTitle()); //支付
    }

    //微信回调
    public void handleNotify(Map<String, String> result) {
        //1数据校验
        payHelper.isSuccess(result);
        //2校验签名
        payHelper.isValidSign(result);

        //3校验金额
        String totalFeeStr = result.get("total_fee");//总金额
        String tradeNo = result.get("out_trade_no");//订单编号
        if(StringUtils.isEmpty(totalFeeStr) || StringUtils.isEmpty(tradeNo)){
            throw new LyException(ExceptionEnum.INVALID_ORDER_PARAM);
        }
        //3.1获取结果中的金额
        long totalFee = Long.valueOf(totalFeeStr);
        //3.2获取订单金额
        long orderId = Long.valueOf(tradeNo);
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(totalFee != /*order.getActualPay()*/ 1){
            //金额不符
            throw new LyException(ExceptionEnum.INVALID_ORDER_PARAM);
        }

        //4修改订单状态
        OrderStatus status = new OrderStatus();
        status.setStatus(OrderStatusEnum.PAYED.value());
        status.setOrderId(orderId);
        status.setPaymentTime(new Date());
        int count = statusMapper.updateByPrimaryKeySelective(status);
        if(count != 1){
            throw new LyException(ExceptionEnum.UPDATE_ORDER_STATUS_ERROR);
        }
        log.info("[订单回调] 订单支付成功！订单编号：{}", orderId);
    }


    //开启定时任务，查询付款状态
    public PayState queryOrderStatue(Long orderId) {
        //查询订单状态
        OrderStatus orderStatus = statusMapper.selectByPrimaryKey(orderId);
        //判断是否支付
        Integer status = orderStatus.getStatus();
        if(status != OrderStatusEnum.UN_PAY.value()){
            //如果已支付，是真的已支付
            return PayState.SUCCESS;
        }

        //如果是未支付，但不一定是未支付，还有其他状态，so 必须去微信查询支付状态
        return payHelper.queryPayState(orderId);

    }

}

















