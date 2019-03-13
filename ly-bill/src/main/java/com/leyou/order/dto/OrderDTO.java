package com.leyou.order.dto;

import com.leyou.common.dto.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;


/**
 * @Author：lidatu
 * @Date： 2019/02/07 星期四 23:52
 * @Description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {  //Data Transfer Object

    @NonNull
    private Long addressId; //收货地址id
    @NonNull
    private Integer paymentType; //付款类型
    @NonNull
    private List<CartDTO> carts; //订单详情

}














